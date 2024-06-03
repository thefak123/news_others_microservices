package com.example.comment_service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.comment_service.model.User;



@Component
public class UserProxy {
    @Autowired
    private RestTemplate restTemplate;

    public User getUserById(Long id){
        // Set headers if necessary
        HttpHeaders httpHeaders = new HttpHeaders();
 

        // Create the HttpEntity with headers
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        // Build the URI with parameters
        String url = "http://localhost:8000/users";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id", id);

        // Create the URI with query parameters
        String uri = builder.toUriString();

        // Make the request and get the response
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                User.class);
        return responseEntity.getBody();
    }
}
