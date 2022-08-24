package xyz.chaobei.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
@Slf4j
public class YamlConfigApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(YamlConfigApplication.class, args);
    }

    @Resource
    private CustomerConfig config;

    @Override
    public void run(String... args) {
        log.info("config name={}", config.getName());
        log.info("config ip={}", config.getIp());
    }
}
