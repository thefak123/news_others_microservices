package com.example.user_service.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user_service.model.User;
import com.example.user_service.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService  {
    @Autowired
    private  UserRepository userRepository;

  

    // @Autowired
    // private JWTService jwtService;

    // @Autowired
    // @Lazy
    // private AuthenticationManager authenticationManager;
    
    

    // Basically override how the spring get the username (make the spring get the username from database)
  
    public User loadUserByUsername(String email)  {
        // TODO Auto-generated method stub
        return userRepository.findUserByEmail(email).orElse(null);
        
    }

    public User loadUserById(Long id)  {
        // TODO Auto-generated method stub
        return userRepository.findUserById(id).orElse(null);
        
    }

    // public ResponseEntity<HashMap<String, Object>> login(User user){
        
    //     String token = jwtService.generateToken(user);
    //     // It will automatically send bad request, when the credentials are not valid
    //     // How can this manager know the username and password ? the manager knows it, because in the security config (check SecurityConfig.java), there is a filter that 
    //     // saves the user credential from db (specificly in the JWTAuthenticationFilter.java)
    //     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    //     HashMap<String, Object> hm = new HashMap<>();
    //         hm.put("token", token);
           
    //     return ResponseEntity.ok(hm);
    // }

    // public ResponseEntity<HashMap<String, Object>> registerUser(User user){
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));;
    //     userRepository.save(user);
    //     String token = jwtService.generateToken(user);
    //     HashMap<String, Object> hm = new HashMap<>();
    //     hm.put("token", token);
    //     return ResponseEntity.ok(hm);

        
    // }

    public ResponseEntity<User> createUser(User user){
        
        userRepository.save(user);
        
        return ResponseEntity.ok(user);

        
    }


}
