package com.example.comment_service.service;


import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.comment_service.model.Comment;
import com.example.comment_service.model.News;
import com.example.comment_service.model.Response;
import com.example.comment_service.proxy.NewsProxy;
import com.example.comment_service.proxy.UserProxy;
import com.example.comment_service.repository.CommentRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private NewsProxy newsProxy;

    public Iterable<Comment> getAllCommentsByNewsId(Long news_id){
        Iterable<Comment> comments = commentRepository.getCommentsByNewsId(news_id);
        List<Comment> out = new ArrayList<>();
        comments.forEach(out::add);
        out = out.stream().map(comment -> {
            comment.setUser(userProxy.getUserById(comment.getSenderId()));
            
            return comment;
        })
        .collect(Collectors.toList());
        return out;
    }

    public Iterable<Comment> getAllComments(){
        Iterable<Comment> comments = commentRepository.findAll();
        List<Comment> out = new ArrayList<>();
        comments.forEach(out::add);
        out = out.stream().map(comment -> {
            comment.setUser(userProxy.getUserById(comment.getSenderId()));
            Response<News> news = newsProxy.getNewsById(comment.getNewsId());
            
            comment.setNews(news.getData());
            return comment;
        })
        .collect(Collectors.toList());
        return out;
    }

    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }
}
