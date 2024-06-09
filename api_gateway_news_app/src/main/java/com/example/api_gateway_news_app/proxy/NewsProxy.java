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

import com.example.api_gateway_news_app.model.News;
import com.example.api_gateway_news_app.model.Response;

import lombok.extern.slf4j.Slf4j;



@Component
@Slf4j
public class NewsProxy {
    @Autowired
    private RestTemplate restTemplate;


    public ResponseEntity<Response<List<News>>> getNews(){
        // Set headers if necessary
        HttpHeaders httpHeaders = new HttpHeaders();
 

        // Create the HttpEntity with headers
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        // Build the URI with parameters
        String url = "http://localhost:8081/api/news";
        
       

        // Make the request and get the response
        try{
            ResponseEntity<Response<List<News>>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Response<List<News>>>() {});
            
                return ResponseEntity.ok(responseEntity.getBody());
        }catch(ResourceAccessException e){
            Response<List<News>> r =  new Response<>();
            r.setSuccess(false);
            r.setMessage("SERVER ERROR");
       
            return ResponseEntity.status(503).body(r);
        }
        
        
    }

    public ResponseEntity<Response<News>> getNewsById(Integer id){
        // Set headers if necessary
        HttpHeaders httpHeaders = new HttpHeaders();
 

        // Create the HttpEntity with headers
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        // Build the URI with parameters
        String url = "http://localhost:8081/api/news/{id}";
        
        HashMap<String, Integer> hm = new HashMap<>();
        hm.put("id", id);

        // Make the request and get the response
        
        try{
            ResponseEntity<Response<News>> responseEntity = restTemplate.exchange(
            url,
            HttpMethod.GET,
            httpEntity,
            new ParameterizedTypeReference<Response<News>>() {}, hm);
            return ResponseEntity.ok(responseEntity.getBody());
        }catch(ResourceAccessException e){
            log.info("error message : ");
            log.info(e.getMessage());
            Response<News> r =  new Response<>();
            r.setSuccess(false);
            r.setMessage("SERVER ERROR");
           
            return ResponseEntity.status(503).body(r);
        }
       
    }

    
}
