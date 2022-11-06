## Spring-Mvc-01


### @EnableMvc
配置类加入如下注解，表示打开MVC自动配置，并且添加如下配置

- HandlerMapping
- HandlerAdapter
- HandlerExceptionResolver

```text
[http-nio-8080-exec-1] INFO xyz.chaobei.controller.IndexController - HandlerMapping,name={item},class=requestMappingHandlerMapping
[http-nio-8080-exec-1] INFO xyz.chaobei.controller.IndexController - HandlerMapping,name={item},class=beanNameHandlerMapping
[http-nio-8080-exec-1] INFO xyz.chaobei.controller.IndexController - HandlerMapping,name={item},class=routerFunctionMapping
[http-nio-8080-exec-1] INFO xyz.chaobei.controller.IndexController - HandlerAdapter,name={item},class=requestMappingHandlerAdapter
[http-nio-8080-exec-1] INFO xyz.chaobei.controller.IndexController - HandlerAdapter,name={item},class=handlerFunctionAdapter
[http-nio-8080-exec-1] INFO xyz.chaobei.controller.IndexController - HandlerAdapter,name={item},class=httpRequestHandlerAdapter
[http-nio-8080-exec-1] INFO xyz.chaobei.controller.IndexController - HandlerAdapter,name={item},class=simpleControllerHandlerAdapter
[http-nio-8080-exec-1] INFO xyz.chaobei.controller.IndexController - HandlerExceptionResolver,name={item},class=handlerExceptionResolver

```

### HandlerMapping



### HandlerMethod

#### ServletRequest/ServletResponse
使用JAVA Servlet api 包下的 `javax.servlet.ServletRequest` `javax.servlet.ServletResponse`
或者是使用 Spring 提供的 `MultipartRequest` `MultipartHttpServletRequest`

#### @RequestBody
将http body直接反序列化为指定对象；通过 `HttpMessageConverter` 进行消息的转换。




#### @PathVariable

#### HttpSession
注意：这是线程不安全的

