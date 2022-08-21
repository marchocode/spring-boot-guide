package xyz.chaobei.resttemplate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class RestTemplateApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateApplication.class, args);
    }

    @Resource
    private RestRequestClient requestClient;

    @Override
    public void run(String... args) {
        requestClient.get("http://v4.ipv6-test.com/api/myip.php");
    }
}
