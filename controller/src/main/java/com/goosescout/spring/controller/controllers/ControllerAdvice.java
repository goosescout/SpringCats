package com.goosescout.spring.controller.controllers;

import com.goosescout.spring.controller.errors.ApiError;
import com.goosescout.spring.controller.errors.DetailedApiError;
import com.goosescout.spring.controller.exceptions.NotAllowedException;
import com.goosescout.spring.service.exceptions.notfound.EntityNotFoundException;
import com.goosescout.spring.service.exceptions.services.CatOwnerServiceException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleEntityNotFoundException(EntityNotFoundException e) {
        return new ApiError(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DetailedApiError handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return new DetailedApiError(
                HttpStatus.BAD_REQUEST,
                "Invalid request body",
                errors
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Could not parse request body"
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DetailedApiError handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        return new DetailedApiError(
                HttpStatus.BAD_REQUEST,
                "Invalid request body",
                String.format("Parameter '%s' is required", e.getParameterName())
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ApiError handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return new ApiError(
                HttpStatus.METHOD_NOT_ALLOWED,
                String.format("Method '%s' is not supported for this endpoint", e.getMethod())
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DetailedApiError handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return new DetailedApiError(
                HttpStatus.BAD_REQUEST,
                "Invalid request body",
                String.format("Invalid value '%s' for parameter '%s'", e.getValue(), e.getName())
        );
    }

    @ExceptionHandler(NotAllowedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleNotAllowedException(NotAllowedException e) {
        return new ApiError(
                HttpStatus.FORBIDDEN,
                e.getMessage()
        );
    }

    @ExceptionHandler(CatOwnerServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleCatOwnerServiceException(CatOwnerServiceException e) {
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleBadCredentialsException(BadCredentialsException e) {
        return new ApiError(
                HttpStatus.UNAUTHORIZED,
                e.getMessage()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleAccessDeniedException(AccessDeniedException e) {
        return new ApiError(
                HttpStatus.FORBIDDEN,
                e.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception e) {
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );
    }
}
