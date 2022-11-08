package xyz.chaobei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import xyz.chaobei.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class IndexController {

    @GetMapping("/")
    public String index(HttpServletRequest request) {

        // find the request's web application context
        WebApplicationContext webApplicationContext = RequestContextUtils.findWebApplicationContext(request);


        // print handler mapping instance
        Map<String, HandlerMapping> beansOfType1 = webApplicationContext.getBeansOfType(HandlerMapping.class);
        for (Map.Entry<String, HandlerMapping> item : beansOfType1.entrySet()) {
            log.info("HandlerMapping,name={},class={}", item.getKey(), item.getValue().getClass().toGenericString());
        }

        // print handler adapter
        Map<String, HandlerAdapter> beansOfType2 = webApplicationContext.getBeansOfType(HandlerAdapter.class);
        for (Map.Entry<String, HandlerAdapter> item : beansOfType2.entrySet()) {
            log.info("HandlerAdapter,name={},class={}", item.getKey(), item.getValue().getClass().toGenericString());
        }

        // print handler exception resolver
        Map<String, HandlerExceptionResolver> beansOfType3 = webApplicationContext.getBeansOfType(HandlerExceptionResolver.class);
        for (Map.Entry<String, HandlerExceptionResolver> item : beansOfType3.entrySet()) {
            log.info("HandlerExceptionResolver,name={},class={}", item.getKey(), item.getValue().getClass().toGenericString());
        }

        // for default. no MultipartResolver has been config.
        Map<String, MultipartResolver> beansOfType = webApplicationContext.getBeansOfType(MultipartResolver.class);

        for (Map.Entry<String, MultipartResolver> item : beansOfType.entrySet()) {
            log.info("MultipartResolver,name={},class={}", item.getKey(), item.getValue().getClass().toString());
        }

        return "index";
    }

    @RequestMapping("/argument")
    public Map argument(String a, String b) {

        Map<String, String> result = new HashMap<>();

        result.put("a", a);
        result.put("b", b);

        return result;
    }

    /**
     * 直接将http body反序列化为参数对象
     *
     * @param user
     * @return
     */
    @PostMapping("/user")
    public String user(@RequestBody User user, HttpServletRequest request) {
        log.info("body反序列化为参数对象={}", user);
        return "success";
    }

    @GetMapping("/header")
    public String header(@RequestHeader("host") String host) {
        log.info("获得请求头={}", host);
        return "success";
    }


    @PostMapping("/upload")
    public String requestPart(@RequestParam("file") MultipartFile multipartFile, @RequestParam("other") String other, HttpServletRequest request) throws IOException {

        // check out MultipartResolver

        // print following info...
        // upload,MultipartResolver,name={item},class=multipartResolver
        WebApplicationContext webApplicationContext = RequestContextUtils.findWebApplicationContext(request);
        Map<String, MultipartResolver> beansOfType = webApplicationContext.getBeansOfType(MultipartResolver.class);
        for (Map.Entry<String, MultipartResolver> item : beansOfType.entrySet()) {
            log.info("upload,MultipartResolver,name={},class={}", item.getKey(), item.getValue().getClass().toString());
        }

        log.info("upload other variable param other={}", other);

        log.info("upload,file name={}", multipartFile.getOriginalFilename());
        log.info("upload,name={}", multipartFile.getName());
        log.info("upload,file size bytes={}", multipartFile.getBytes().length);

        return "success";
    }


    @PostMapping("/upload/multipart")
    public String requestPart(MultipartHttpServletRequest request) throws IOException {

        String other = request.getParameter("other");
        MultipartFile multipartFile = request.getFile("file");

        log.info("upload other variable param other={}", other);

        log.info("upload,file name={}", multipartFile.getOriginalFilename());
        log.info("upload,name={}", multipartFile.getName());
        log.info("upload,file size bytes={}", multipartFile.getBytes().length);

        return "success";
    }

    @PostMapping("/entity")
    public String accessHttpEntity(HttpEntity<User> httpEntity) {

        // access headers
        HttpHeaders headers = httpEntity.getHeaders();

        headers.forEach((str, list) -> {
            log.info("entity access header={},value={}", str, Arrays.toString(list.toArray()));
        });

        // access body
        User user = httpEntity.getBody();
        log.info("entity access body={}", user);

        return "success";
    }


}
