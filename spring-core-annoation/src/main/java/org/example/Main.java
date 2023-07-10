package org.example;

import org.example.config.AppConfig;
import org.example.services.DbService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("org.example");
        context.refresh();

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String str : beanDefinitionNames) {
            System.out.println(str);
        }

        DbService dbService = context.getBean("dbService", DbService.class);
        dbService.select();

    }
}