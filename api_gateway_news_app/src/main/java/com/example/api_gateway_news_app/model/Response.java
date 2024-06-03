package com.example.api_gateway_news_app.model;

import lombok.Data;

@Data
public class Response<T> {
    boolean success;
    String message;
    T data;
}
