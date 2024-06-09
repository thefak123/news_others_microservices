package com.example.comment_service.model;

import lombok.Data;

@Data
public class News {
    long id;
    String title;
    String content;

    String image_url;
    String category;

}
