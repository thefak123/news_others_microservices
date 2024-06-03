package com.example.api_gateway_news_app.service;

import java.net.ConnectException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.api_gateway_news_app.model.User;
import com.example.api_gateway_news_app.proxy.UserProxy;
// import com.example.api_gateway_news_app.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    // @Autowired
    // private  UserRepository userRepository;

    @Autowired
    private UserProxy userProxy;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    
    

    // Basically override how the spring get the username (make the spring get the username from database)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return userProxy.getUserByEmail(email);
        
    }

    public ResponseEntity<HashMap<String, Object>> login(User user){
        HashMap<String, Object> hm = new HashMap<>();
        try{
            String token = jwtService.generateToken(user);
            // It will automatically send bad request, when the credentials are not valid
            // How can this manager know the username and password ? the manager knows it, because in the security config (check SecurityConfig.java), there is a filter that 
            // saves the user credential from db (specificly in the JWTAuthenticationFilter.java)
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            hm.put("message", "success");
            hm.put("token", token);
            
            return ResponseEntity.ok(hm);
        }catch (InternalAuthenticationServiceException e) {
            // Handle the connection error
            log.info("InternalAuthenticationServiceException");
            hm.put("token", null);
            hm.put("message", e.getMessage());
            if(e.getMessage().equals("401 : [no body]")){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(hm);
            }
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(hm);
        }  
    }

    public ResponseEntity<HashMap<String, Object>> registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userProxy.saveUser(user);
        String token = jwtService.generateToken(user);
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("token", token);
        return ResponseEntity.ok(hm);

        
    }
}
