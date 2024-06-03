package com.example.api_gateway_news_app.model;

import java.util.Date;



// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Comment{
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

}

