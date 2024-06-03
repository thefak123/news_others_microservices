package com.example.api_gateway_news_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_gateway_news_app.model.News;
import com.example.api_gateway_news_app.model.Response;
import com.example.api_gateway_news_app.proxy.NewsProxy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/news")
public class NewsController {
    
    @Autowired
    NewsProxy newsProxy;

    @GetMapping
    public ResponseEntity<Response<List<News>>> getAllNews() {
  

    
        return newsProxy.getNews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<News>> getNewsById(@PathVariable("id") Integer id) {
        


    
        return newsProxy.getNewsById(id);
    }
    
}
