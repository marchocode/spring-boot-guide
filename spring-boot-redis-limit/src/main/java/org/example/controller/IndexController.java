package org.example.controller;

import org.example.annotions.Limit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    @Limit(period = 60, count = 5)
    public String index() {
        return "success";
    }


}
