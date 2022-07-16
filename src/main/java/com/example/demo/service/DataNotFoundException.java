package com.example.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {

    public static final long serialVersionUID = 1L;
    public DataNotFoundException(String question_not_found) {
        super(question_not_found);
    }
}
