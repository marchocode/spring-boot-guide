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

### 请求参数

#### ServletRequest/ServletResponse
使用JAVA Servlet api 包下的 `javax.servlet.ServletRequest` `javax.servlet.ServletResponse`
或者是使用 Spring 提供的 `MultipartRequest` `MultipartHttpServletRequest`

**如果使用 `MultipartRequest` 作为方法的参数，则需要注册一个`MultipartResolver` **


#### @RequestBody
将http body直接反序列化为指定对象；通过 `HttpMessageConverter` 进行消息的转换。

#### HttpEntity<T>
请求参数内包含完整的HTTP请求头和完整的请求体。

#### @PathVariable
通过从请求路径上获取参数

#### HttpSession
注意：这是线程不安全的

### 响应参数

#### @ResponseBody
常用，直接将返回值写入响应体, 通过 `HttpMessageConverter` 序列化对象


#### HttpEntity/ResponseEntity
包含完整的响应，包含请求头和请求体的完整响应对象。


#### HttpHeaders
只包含响应头而没有响应主体

### 参数转换
在参数进行入参的时候，基于String的请求类型类型(request parameters, path variables, headers, cookies)，以及基础类型 `int,Date,long` 等类型都可以进行自动参数转换，如果是其他类型，就需要手动定义转换




### Reference

- https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-typeconversion
- 