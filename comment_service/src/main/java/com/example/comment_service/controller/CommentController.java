package com.example.comment_service.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.comment_service.model.Comment;
import com.example.comment_service.service.CommentService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.comment_service.model.Response;

@RestController
@Slf4j
@RequestMapping("v1/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<Response<Iterable<Comment>>> getAllComments(@RequestParam(name = "newsid", required = true) Long newsId){
        Response<Iterable<Comment>> response = new Response<>();
        try {
            
            response.setData(commentService.getAllCommentsByNewsId(newsId));
            response.setMessage("Get All Comments");
            response.setSuccess(true);
            return ResponseEntity.ok(response);
            
            
        }catch(Error e){
            log.error(e.getMessage(), e);
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return ResponseEntity.status(500).body(response);
        }
        
    }

    @GetMapping("/all")
    public ResponseEntity<Response<Iterable<Comment>>> getAllComments(){
        Response<Iterable<Comment>> response = new Response<>();
        try {
            
            response.setData(commentService.getAllComments());
            response.setMessage("Get All Comments");
            response.setSuccess(true);
            return ResponseEntity.ok(response);
            
            
        }catch(Error e){
            log.error(e.getMessage(), e);
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return ResponseEntity.status(500).body(response);
        }
        
    }

    @PostMapping
    public ResponseEntity<Response<Comment>> createComment(@RequestBody Comment comment){
        Response<Comment> response = new Response<>();
        try {
            
            comment.setCreated(new Date());
            
            response.setData(commentService.createComment(comment));
            response.setMessage("Create");
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }catch(Error e){
            log.error(e.getMessage(), e);
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return ResponseEntity.status(500).body(response);
        }
    }
}
