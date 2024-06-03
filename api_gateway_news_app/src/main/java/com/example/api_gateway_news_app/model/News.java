package com.example.api_gateway_news_app.model;

import java.util.Date;

import lombok.Data;

@Data
public class News {
    String id;
    String title;
    String content;
    String image_url;
    String category;
    Date created_at;
    Date updated_at;
}
