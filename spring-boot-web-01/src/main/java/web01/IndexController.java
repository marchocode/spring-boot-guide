package web01;

import org.apache.catalina.core.ApplicationFilterRegistration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;
import javax.servlet.FilterRegistration;
import java.util.Map;

@RestController
public class IndexController {

    @Resource
    private DispatcherServlet dispatcherServlet;

    @GetMapping("/")
    public String index() {

        Map<String, ? extends FilterRegistration> servletRegistrations = dispatcherServlet.getServletContext().getFilterRegistrations();

        for (Map.Entry<String, ? extends FilterRegistration> item : servletRegistrations.entrySet()) {
            System.out.printf("register name=%s,value=%s\n", item.getKey(), item.getValue().getClassName());
        }

        return "index";
    }

}
