## Spring-Mvc

### 配置一个Spring-MVC

#### Traditional Config

通过传统的方式配置,需要配置 web.xml 以下是web.xml文件内容

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://jakarta.ee/xml/ns/jakartaee https://jakarta.ee/xml/ns/jakartaee/web-app_5_0.xsd"
         version="5.0">

    <servlet>
        <servlet-name>app</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/app-context.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <servlet-mapping>
        <servlet-name>app</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>

</web-app>
```

#### 无XML配置的方式

> https://docs.oracle.com/javaee/6/api/javax/servlet/ServletContainerInitializer.html
> Interface which allows a library/runtime to be notified of a web application's startup phase and perform any required programmatic registration of servlets, filters, and listeners in response to it.

通过 `ServletContainerInitializer`来实现无 `web.xml` 的方式配置servlet

```java
public class MyWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
```

### DispatcherServlet
Spring-MVC 处理前端请求的组件, 唯一的一个servlet, 用户接收请求。

- 通过web.xml 和绝大多数servlet一样进行初始化和配置
- 通过spring的配置初始化

dispatcherServlet 需要一个 ApplicationContext 作为配置

#### HandlerMapping
请求映射处理，将不同的请求映射到不同的处理器中。

两个重要的实现

- RequestMappingHandlerMapping: 支持通过注解的方式添加请求处理器@RequestMapping
- SimpleUrlHandlerMapping

#### HandlerAdapter
处理适配器，关联请求所需要执行的方法；

```text
@Getmapping("/hello")
public void hello() {
    xxx
}
```

#### HandlerExceptionResolver 
处理器的异常处理。

1. 如果在请求mapping中发生异常
2. 或者controller 抛出一个异常

则dispatchServlet 将异常信息和请求交给 HandlerExceptionResolver 链来处理。

```java
	@Nullable
	ModelAndView resolveException(
			HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex);
```

1. 返回null 不能解析，用下一个处理器处理
2. 返回empty 已经被处理
3. 不是empty 包含错误页面

如果异常处理链都没有正常处理的话，则会冒泡上浮到Servlet容器

#### @EnableMvc
配置类加入如下注解，表示打开MVC自动配置，并且添加如下默认配置

#### Default Implements
> The DispatcherServlet checks the WebApplicationContext for each special bean. 
> If there are no matching bean types, it falls back on the default types listed in DispatcherServlet.properties.

DispatchServlet 中，检查Application Context中是否持有对应类型的Bean, 如果没有，则默认使用以下这些。

```text
# Default implementation classes for DispatcherServlet's strategy interfaces.
# Used as fallback when no matching beans are found in the DispatcherServlet context.
# Not meant to be customized by application developers.

# 区域处理(国际化)
org.springframework.web.servlet.LocaleResolver=org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver

# 主题处理
org.springframework.web.servlet.ThemeResolver=org.springframework.web.servlet.theme.FixedThemeResolver

# 请求映射
org.springframework.web.servlet.HandlerMapping=org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,\
	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping,\
	org.springframework.web.servlet.function.support.RouterFunctionMapping

# 请求处理
org.springframework.web.servlet.HandlerAdapter=org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,\
	org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,\
	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter,\
	org.springframework.web.servlet.function.support.HandlerFunctionAdapter

# 请求异常处理
org.springframework.web.servlet.HandlerExceptionResolver=org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver,\
	org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver,\
	org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver

org.springframework.web.servlet.RequestToViewNameTranslator=org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator

org.springframework.web.servlet.ViewResolver=org.springframework.web.servlet.view.InternalResourceViewResolver

org.springframework.web.servlet.FlashMapManager=org.springframework.web.servlet.support.SessionFlashMapManager
```



#### Interception
> https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-handlermapping-interceptor

所有的 HandlerMapping 都支持 拦截器 Interception, 实现拦截器，必须实现 `org.springframework.web.servlet.HandlerInterceptor`
有主要的三个方法：

1. preHandler() 处理程序之前
   1. 如果返回true 则继续执行链
   2. 如果返回false, 停止执行，DispatchServlet假设拦截器已经返回了一个合适的视图或者响应内容。
2. postHandler() 处理程序之后
3. afterCompletion() 请求完成之后


### Filter

#### FormContentFilter
Servlet API的 `getParameter` 接口，只支持获得GET请求的query参数 或者是 POST请求的from-data参数。
但是对于PUT/DELETE/PATCH 类型，则无法处理。

FormContentFilter 过滤器使得PUT/DELETE/PATCH 支持form-data类型的请求格式。
**注意：以上的三个类型请求其请求格式必须为 `application/x-www-form-urlencoded` **

```java
    /**
     * Returns the value of a request parameter as a <code>String</code>,
     * or <code>null</code> if the parameter does not exist. Request parameters
     * are extra information sent with the request.  For HTTP servlets,
     * parameters are contained in the query string or posted form data.
     *
     * <p>You should only use this method when you are sure the
     * parameter has only one value. If the parameter might have
     * more than one value, use {@link #getParameterValues}.
     *
     * <p>If you use this method with a multivalued
     * parameter, the value returned is equal to the first value
     * in the array returned by <code>getParameterValues</code>.
     *
     * <p>If the parameter data was sent in the request body, such as occurs
     * with an HTTP POST request, then reading the body directly via {@link
     * #getInputStream} or {@link #getReader} can interfere
     * with the execution of this method.
     *
     * @param name a <code>String</code> specifying the name of the parameter
     *
     * @return a <code>String</code> representing the single value of
     * the parameter
     *
     * @see #getParameterValues
     */
    public String getParameter(String name);
```

#### CorsFilter
CORS = `Cross Origin Resource Sharing` 跨越源资源共享，跨域请求配置



### Declaration 声明

#### Controller and RestController
声明一个类作为请求映射，并且注册到 DispatchServlet. 用于接受和处理请求。
RestController 是一个组合注解，其表明直接将返回的内容写入response body.

#### ComponentScan
自动发现与扫描，将对象注册到 DispatchServlet.

#### 请求映射

多数情况下，应该使用 xxxMapping 注解将一个方法映射到指定的http type中

- RequestMapping
- GetMapping
- PostMapping
- PutMapping
- DeleteMapping
- PatchMapping



#### 



### Reference

- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/WebApplicationInitializer.html
- https://docs.oracle.com/javaee/6/api/javax/servlet/ServletContainerInitializer.html

