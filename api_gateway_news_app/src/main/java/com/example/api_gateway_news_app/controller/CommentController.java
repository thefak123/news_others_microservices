package com.example.api_gateway_news_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_gateway_news_app.model.Comment;
import com.example.api_gateway_news_app.model.CommentCustom;
import com.example.api_gateway_news_app.model.Response;
import com.example.api_gateway_news_app.proxy.CommentProxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/comments")

public class CommentController {
    @Autowired
    private CommentProxy commentProxy;

    @GetMapping
    public ResponseEntity<Response<List<Comment>>> getAllComments(@RequestParam(name="newsid", required = true) String news_id) {
        try{
            return commentProxy.getAllCommentsByNewsId(news_id);
        }catch(Error e){
            Response<List<Comment>> response = new Response<>();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return ResponseEntity.status(500).body(null);
        }
        
    }

    @PostMapping
    public ResponseEntity<Response<Comment>> createComment(@RequestBody Comment comment) {
        try{
            return commentProxy.createComment(comment);
        }catch(Error e){
            Response<Comment> response = new Response<>();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return ResponseEntity.status(500).body(response);
        }
        
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<CommentCustom>>> getAllComments() {
        try{
            return commentProxy.getAllComments();
        }catch(Error e){
            Response<List<CommentCustom>> response = new Response<>();
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return ResponseEntity.status(500).body(response);
        }
        
    }
    
}
