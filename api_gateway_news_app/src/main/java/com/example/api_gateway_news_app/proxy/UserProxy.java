package com.example.api_gateway_news_app.proxy;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.api_gateway_news_app.model.CommentCustom;
import com.example.api_gateway_news_app.model.News;
import com.example.api_gateway_news_app.model.Response;
import com.example.api_gateway_news_app.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserProxy {
    @Autowired
    private RestTemplate restTemplate;

    public User getUserByEmail(String email){
        // Set headers if necessary
        HttpHeaders httpHeaders = new HttpHeaders();
 

        // Create the HttpEntity with headers
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        // Build the URI with parameters
        String url = "http://localhost:8000/users";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("email", email);

        // Create the URI with query parameters
        String uri = builder.toUriString();

        // Make the request and get the response
        try{
            ResponseEntity<User> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                User.class);
                responseEntity.getBody();
            return responseEntity.getBody();
        }catch(Error e ){
            log.info("error");
            log.info(e.getMessage());
            return null;
        }
        
        
        
    }

    public User saveUser(User user){
        // Set headers if necessary
        HttpHeaders httpHeaders = new HttpHeaders();
 

        // Create the HttpEntity with headers
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        // Build the URI with parameters
        String url = "http://localhost:8000/users";
       

        

        // Make the request and get the response
        ResponseEntity<User> responseEntity = restTemplate.exchange(
            url,
                HttpMethod.POST,
                httpEntity,
                User.class);
        return responseEntity.getBody();
    }

    public ResponseEntity<List<User>> getAllUsers(){
        // Set headers if necessary
        HttpHeaders httpHeaders = new HttpHeaders();
 

        HttpEntity<User> httpEntity = new HttpEntity<>(null, httpHeaders);

        // Build the URI with parameters
        String url = "http://localhost:8000/users/all";
       


     
        
        try{
             // Make the request and get the response
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
            url,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<List<User>>() {});
            return responseEntity;
        }catch(ResourceAccessException e){
            log.info("error message : ");
            log.info(e.getMessage());
         
           
            return ResponseEntity.status(503).body(null);
        }
        
    }
}
