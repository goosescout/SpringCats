package com.goosescout.spring.controller.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ApiError {
    protected HttpStatus status;
    @Getter
    protected String error;

    public int getStatus() {
        return status.value();
    }
}
