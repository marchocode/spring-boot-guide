## Spring-Boot-Web


### DispatcherServlet



### Filter

````text
2022-11-03 21:14:27.007  INFO 25076 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 642 ms
2022-11-03 21:14:27.010 DEBUG 25076 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'requestContextFilter'
2022-11-03 21:14:27.013 DEBUG 25076 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'formContentFilter'
2022-11-03 21:14:27.015 DEBUG 25076 --- [           main] o.s.b.f.s.DefaultListableBeanFactory     : Creating shared instance of singleton bean 'characterEncodingFilter'
````

```text
2022-11-03 21:14:27.021 DEBUG 25076 --- [           main] o.s.b.w.s.ServletContextInitializerBeans : Mapping filters: characterEncodingFilter urls=[/*] order=-2147483648, formContentFilter urls=[/*] order=-9900, requestContextFilter urls=[/*] order=-105
2022-11-03 21:14:27.021 DEBUG 25076 --- [           main] o.s.b.w.s.ServletContextInitializerBeans : Mapping servlets: dispatcherServlet urls=[/]
```

- characterEncodingFilter urls=[/*] order=-2147483648
- formContentFilter urls=[/*] order=-9900
- requestContextFilter urls=[/*] order=-105


```text
register name=requestContextFilter,value=org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter
register name=Tomcat WebSocket (JSR356) Filter,value=org.apache.tomcat.websocket.server.WsFilter
register name=characterEncodingFilter,value=org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter
register name=formContentFilter,value=org.springframework.boot.web.servlet.filter.OrderedFormContentFilter
```