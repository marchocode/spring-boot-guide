package example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;
import javax.servlet.FilterRegistration;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    private DispatcherServlet dispatcherServlet;

    @GetMapping("/")
    public String index() {

        Map<String, ? extends FilterRegistration> servletRegistrations = dispatcherServlet.getServletContext().getFilterRegistrations();

        for (Map.Entry<String, ? extends FilterRegistration> item : servletRegistrations.entrySet()) {
            System.out.printf("register name=%s", item.getKey());
        }

        return "index";
    }

}
