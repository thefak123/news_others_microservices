package com.example.api_gateway_news_app.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.api_gateway_news_app.model.Comment;
import com.example.api_gateway_news_app.model.CommentCustom;
import com.example.api_gateway_news_app.model.News;
import com.example.api_gateway_news_app.model.Response;
import com.example.api_gateway_news_app.model.User;

import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommentProxy {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserProxy userProxy;

    public ResponseEntity<Response<List<Comment>> >
    getAllCommentsByNewsId(String news_id){
        // Set headers if necessary
        HttpHeaders httpHeaders = new HttpHeaders();
 

        // Create the HttpEntity with headers
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        // Build the URI with parameters
        String url = "http://localhost:7000/v1/comments";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("newsid", news_id);

        // Create the URI with query parameters
        String uri = builder.toUriString();

        // Make the request and get the response
        
        try{
            ResponseEntity<Response<List<Comment>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Response<List<Comment>>>() {});
            return ResponseEntity.ok(responseEntity.getBody());
        }catch(ResourceAccessException e){
            Response<List<Comment>> r =  new Response<>();
            r.setSuccess(false);
            r.setMessage("SERVER ERROR");
            
            return ResponseEntity.status(503).body(r);
        }catch(Error e){
            Response<List<Comment>> r =  new Response<>();
            r.setSuccess(false);
            r.setMessage("SERVER ERROR");
            
            return ResponseEntity.status(500).body(r);
        }
        
    }

    public ResponseEntity<Response<Comment>> createComment(Comment comment){
        HttpHeaders httpHeaders = new HttpHeaders();
        // httpHeaders.add("requestId", UUID.randomUUID().toString());
        HttpEntity<Comment> httpEntity = new HttpEntity<>(comment, httpHeaders);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email =  ((UserDetails) authentication.getPrincipal()).getUsername();
        log.info("email : ", email);
        User user = userProxy.getUserByEmail(email);
        comment.setSenderId(user.getId());
        try{
            ResponseEntity<Response<Comment>> responseEntity = restTemplate.exchange("http://localhost:7000/v1/comments", HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Response<Comment>>() {});
            return ResponseEntity.ok(responseEntity.getBody());
        }catch(ResourceAccessException e){
            Response<Comment> r =  new Response<>();
            r.setSuccess(false);
            r.setMessage("SERVER ERROR");
            
            return ResponseEntity.status(503).body(r);
        }catch(Error e){
            Response<Comment> r =  new Response<>();
            r.setSuccess(false);
            r.setMessage("SERVER ERROR");
            
            return ResponseEntity.status(500).body(r);
        }
        
    }

    public ResponseEntity<Response<List<CommentCustom>>> getAllComments(){
        // Set headers if necessary
        HttpHeaders httpHeaders = new HttpHeaders();
 

        // Create the HttpEntity with headers
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        // Build the URI with parameters
        String url = "http://localhost:7000/v1/comments/all";
        



        // Make the request and get the response
        
        try{
            ResponseEntity<Response<List<CommentCustom>>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<Response<List<CommentCustom>>>() {});
            log.info(responseEntity.getBody().toString());
            return ResponseEntity.ok(responseEntity.getBody());
        }catch(ResourceAccessException e){
            Response<List<CommentCustom>> r =  new Response<>();
            r.setSuccess(false);
            r.setMessage("SERVER ERROR");
            
            return ResponseEntity.status(503).body(r);
        }catch(Error e){
            Response<List<CommentCustom>> r =  new Response<>();
            r.setSuccess(false);
            r.setMessage("SERVER ERROR");
            
            return ResponseEntity.status(500).body(r);
        }
        
    }
}
