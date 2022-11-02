## Spring-boot-security-simple
Springboot 约定大于配置


### Getting Start

```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```

无需任何配置，默认加载一套认证体系，默认生成登录页面以及用户名和密码

1. 生成登录接口 /login
2. 生成登录页面


- 用户名：user
- 密码：自动生成

### 重要的对象

- SecurityFilterChain 重要的过滤器链，配置过滤器顺序

```text
class: org.springframework.security.web.session.DisableEncodeUrlFilter
class: org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter
class: org.springframework.security.web.context.SecurityContextPersistenceFilter
class: org.springframework.security.web.header.HeaderWriterFilter
class: org.springframework.security.web.csrf.CsrfFilter
class: org.springframework.security.web.authentication.logout.LogoutFilter
class: org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
class: org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter
class: org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter
class: org.springframework.security.web.authentication.www.BasicAuthenticationFilter
class: org.springframework.security.web.savedrequest.RequestCacheAwareFilter
class: org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
class: org.springframework.security.web.authentication.AnonymousAuthenticationFilter
class: org.springframework.security.web.session.SessionManagementFilter
class: org.springframework.security.web.access.ExceptionTranslationFilter
class: org.springframework.security.web.access.intercept.FilterSecurityInterceptor

```


### References

- https://docs.spring.io/spring-security/reference/servlet/getting-started.html