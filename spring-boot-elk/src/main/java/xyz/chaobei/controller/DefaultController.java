package xyz.chaobei.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class DefaultController {

    @GetMapping
    public ResponseEntity<String> get() {
        log.info("hello");
        return ResponseEntity.ok("hello");
    }

}
