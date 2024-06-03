package com.example.api_gateway_news_app.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.api_gateway_news_app.model.User;
import com.example.api_gateway_news_app.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, Object>> login(@RequestBody User user){

        return userService.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, Object>> register(@RequestBody User user){
        try{
            return userService.registerUser(user);
        }catch(Error e){
            log.info(e.getMessage());
            return ResponseEntity.ok(null);
        }
        
    }
}
