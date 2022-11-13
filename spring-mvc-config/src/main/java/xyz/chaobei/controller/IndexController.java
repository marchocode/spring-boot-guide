package xyz.chaobei.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.chaobei.entity.User;

@RestController
@Slf4j
public class IndexController {

    @GetMapping("/")
    public String index() {
        log.info("execute index");
        return "index";
    }

    @GetMapping("/user")
    public User user(User user) {
        log.info("receive user info ={}", user);
        return user;
    }

    @PostMapping("/user")
    public User userPost(User user) {
        log.info("receive Post user info ={}", user);
        return user;
    }

    @PostMapping("/validation")
    public User validation(@Validated User user) {
        log.info("validation Post user info ={}", user);
        return user;
    }

    @PostMapping("/json")
    public User json(@RequestBody User user) {
        log.info("receive Post json user info ={}", user);
        return user;
    }


}
