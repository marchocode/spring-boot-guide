package xyz.chaobei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.chaobei.repository.UserEntityRepository;

@RestController
public class UserController {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity get(@PathVariable String id) {
        Long ids = Long.valueOf(id);
        return ResponseEntity.ok(userEntityRepository.findById(ids).get());
    }

}
