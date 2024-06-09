package com.example.api_gateway_news_app.model;

import java.util.Date;

import lombok.Data;

@Data
public class CommentCustom {
    // @Id
    
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // @Column(nullable = false)
    String message;

    // @Column(nullable = false)
    Long senderId; // it will automatically set to the sender_id in the database

    // @Column(nullable = false)
    Long newsId; // it will automatically set to the news_id in the database

    // @Column(nullable = false)
    Date created;


    UserInfo user;

    News news;
}
