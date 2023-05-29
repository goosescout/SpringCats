package com.goosescout.spring.controller.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class DetailedApiError extends ApiError {
    @Getter
    protected List<String> details;

    public DetailedApiError(HttpStatus status, String error, List<String> details) {
        super(status, error);
        this.details = details;
    }

    public DetailedApiError(HttpStatus status, String error, String detail) {
        super(status, error);
        this.details = Collections.singletonList(detail);
    }
}
