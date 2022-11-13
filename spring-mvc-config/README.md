## Spring-MVC-Config

spring mvc config 详解

### @EnableWebMvc

通过这个注解，打开默认的MVC配置，默认的springMVC配置会注册一些特殊的对象：

- HandlerMapping
- HandlerAdapter
- ...

### 自定义你的配置

实现WebMvcConfigurer 接口，并重写所需要的方法

```java
public class AppConfig implements WebMvcConfigurer {
    // ...
}
```

#### 注册类型转换

注册类型转换的目的是：spring 会将 servlet 接受到的 string 类型的参数转换为对应类型：
并且可以自定义转换类型：

- String -> Integer
- String -> Long
- String -> LocalDate

```java
    private static class StringToLocalDateTime implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        log.info("StringToLocalDateTime source={}", source);
        Date date = new Date(Long.valueOf(source));
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
```

DateFormatterRegister 提供很多有关时间的相互转换：定义自定义的时间转换

```java
    DateTimeFormatterRegistrar dateTimeFormatterRegistrar=new DateTimeFormatterRegistrar();

        DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.CHINESE);
        DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("HH:mm:ss",Locale.CHINESE);
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss",Locale.CHINESE);

        dateTimeFormatterRegistrar.setDateFormatter(dateFormatter);
        dateTimeFormatterRegistrar.setTimeFormatter(timeFormatter);
        dateTimeFormatterRegistrar.setDateTimeFormatter(dateTimeFormatter);
        // register many dateTime
        dateTimeFormatterRegistrar.registerFormatters(registry);
```

#### 拦截器

拦截器提供了一种，在执行指定的 handler 之前，之后，完成请求之后都有一个对应的钩子函数，可供完成一些特殊的任务：

```java
public interface HandlerInterceptor {

    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            @Nullable ModelAndView modelAndView) throws Exception {
    }

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {
    }

}
```

1. preHandle 执行 handler 之前，如果返回 true ,则继续执行拦截器链，如果返回false 则中断执行。直接返回信息
2. postHandle 成功执行 handler ，并且没有任何异常信息
3. afterCompletion 完成整个请求之后，也可能是执行 handler 之中，发生异常导致方法退出，则会被 afterCompletion 执行。

**拦截器不适合作为安全层来处理安全，建议使用Spring-security**

#### 消息转换

消息转换适用于 带有 `@RequestBody` 注解的内容，用于将http body 内的内容转换(反序列化)
为指定的类型：以及将带有 `@ResponseBody` 的返回类型
将内容序列化为指定类型。

```java
    protected final List<HttpMessageConverter<?>>getMessageConverters(){
        if(this.messageConverters==null){
        this.messageConverters=new ArrayList<>();
        // 配置消息转换器
        configureMessageConverters(this.messageConverters);
        // 检查为空，就增加默认的消息转换器
        if(this.messageConverters.isEmpty()){
        addDefaultHttpMessageConverters(this.messageConverters);
        }
        // 扩展消息转换器
        extendMessageConverters(this.messageConverters);
        }
        return this.messageConverters;
        }
```

Spring 支持 jackson 作为json序列化与反序列化的工具；

```java
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();

        jackson2ObjectMapperBuilder.indentOutput(true);
        jackson2ObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);

        /**
         * 配置jdk8的 localDateTime 序列化与反序列化方式
         */
        jackson2ObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        jackson2ObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));

        converters.add(new MappingJackson2HttpMessageConverter(jackson2ObjectMapperBuilder.build()));

```

默认 jackson 不支持Jdk8中新的日期和时间类型， 例如 `LocalDateTime` `LocalDate` ,添加包默认支持：

```xml
        <!-- 支持JAVA8的日期和时间类型 -->
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
        </dependency>
```

#### 访问静态资源



### ERROR

### Cannot construct instance of `xyz.chaobei.entity.User` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
反序列化中：需要一个空的构造器，默认需要一个构造器