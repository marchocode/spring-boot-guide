package example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.ServletRegistration;
import java.util.Map;

@SpringBootApplication
public class SimpleSecurityApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSecurityApplication.class, args);
    }

    @Resource
    private SecurityFilterChain securityFilterChain;

    @Override
    public void run(String... args) throws Exception {
        for (Filter filter : securityFilterChain.getFilters()) {
            System.out.printf("class: %s\n", filter.getClass().getName());
        }

    }
}
