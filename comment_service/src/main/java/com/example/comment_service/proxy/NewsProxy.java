package com.example.comment_service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.comment_service.model.News;
import com.example.comment_service.model.Response;

@Component
public class NewsProxy {
    @Autowired
    private RestTemplate restTemplate;

    public Response<News> getNewsById(Long id){
        // Set headers if necessary
        HttpHeaders httpHeaders = new HttpHeaders();
 

        // Create the HttpEntity with headers
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        // Build the URI with parameters
        String url = "http://localhost:8081/api/news/{id}";
    
         ParameterizedTypeReference<Response<News>> responseType = new ParameterizedTypeReference<Response<News>>() {};
      

        // Make the request and get the response
        ResponseEntity<Response<News>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                responseType, id);
        return responseEntity.getBody();
    }
}
