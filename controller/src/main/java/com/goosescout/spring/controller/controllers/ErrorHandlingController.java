package com.goosescout.spring.controller.controllers;

import com.goosescout.spring.controller.errors.ApiError;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandlingController implements ErrorController {
    @RequestMapping(value = "/error")
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiError handleResourceNotFound() {
        return new ApiError(
                HttpStatus.NOT_FOUND,
                "The requested resource could not be found"
        );
    }
}
