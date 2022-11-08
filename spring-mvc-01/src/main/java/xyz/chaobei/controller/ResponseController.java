package xyz.chaobei.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.chaobei.entity.User;

@Controller
public class ResponseController {


    @GetMapping("/response/json")
    @ResponseBody
    public User user() {
        return new User(1l, "java");
    }

    @GetMapping("/response/full")
    public HttpEntity full() {


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("token");
        headers.setAccessControlAllowOrigin("http://localhost");

        HttpEntity httpEntity = new HttpEntity("success", headers);

        // the following example has same result.
        //
        // ResponseEntity.ok().headers(headers).body("success");

        return httpEntity;
    }

    @GetMapping("/response/only-header")
    public HttpHeaders onlyHeader() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("token");
        headers.setAccessControlAllowOrigin("http://localhost");

        return headers;
    }


}
