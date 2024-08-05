package com.study.tesma.controller;

import com.study.tesma.entity.User;
import com.study.tesma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAPIController {
    @Autowired
    private UserService userService;

    @PostMapping("/userAPI")
    public User login(@RequestParam("userName") String username, @RequestParam("password") String password) {
        return userService.login(username, password);
    }
}
