package com.example.user_service.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.user_service.model.User;
import com.example.user_service.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/users")
@Slf4j
public class AuthController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getAllUsers(){
        try{
            
            
            
            return ResponseEntity.ok(userService.getAllUsers());
                
           
            
        }catch(Error e){
        
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam(name="email", required=false) String email, @RequestParam(name="id", required=false) Long id){
        try{
            if(email != null){
                User user = userService.loadUserByUsername(email);
                if (user != null){
                    return ResponseEntity.ok(user);
                }else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
                }
                
            }else{
                log.info("going through id");
                return ResponseEntity.ok(userService.loadUserById(id));
            }
            
        }catch(Error e){
        
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }

    

    @PostMapping
    
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            return userService.createUser(user);
        }catch(Error e){
            log.info(e.getMessage());
            return ResponseEntity.ok(null);
        }
        
    }
}
