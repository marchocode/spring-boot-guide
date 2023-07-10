package org.example.config;

import org.example.pojo.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Student student() {
        return new Student();
    }

}
