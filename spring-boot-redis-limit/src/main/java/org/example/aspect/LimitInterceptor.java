package org.example.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.annotions.Limit;
import org.example.enums.LimitType;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Configuration
@Slf4j
@RequiredArgsConstructor
public class LimitInterceptor {

    private final RedisTemplate<String, Serializable> redisTemplate;
    private static final String UNKNOWN = "unknown";
    private RedisScript<Long> redisScript;

    @PostConstruct
    public void init() {
        redisScript = RedisScript.of(buildLua(), Long.class);
    }

    private String buildLua() {

        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        // 调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }

    @Around("execution(public * *(..)) && @annotation(org.example.annotions.Limit)")
    public Object interceptor(ProceedingJoinPoint point) {

        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();
        Limit limit = method.getAnnotation(Limit.class);
        LimitType limitType = limit.type();

        String key = null;
        int limitPeriod = limit.period();
        int limitCount = limit.count();

        log.info("received a request limitPeriod={},count={}", limitPeriod, limitCount);

        switch (limitType) {
            case CUSTOMER:
                key = method.getName();
                break;
            case IP:
                key = getIpAddress();
                break;
        }

        try {

            List<String> keys = Arrays.asList(key);

            Number count = redisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
            log.info("redis key={}, count={}", key, count);

            if (count != null && count.intValue() <= limitCount) {
                return point.proceed();
            } else {
                throw new RuntimeException("You have been dragged into the blacklist");
            }

        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }
    }


    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
