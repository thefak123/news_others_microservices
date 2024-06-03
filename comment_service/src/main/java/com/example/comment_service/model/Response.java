package com.example.comment_service.model;

import lombok.Data;

@Data
public class Response<T> {
    boolean success;
    String message;
    T data;
}
