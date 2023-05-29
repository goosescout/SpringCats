package com.goosescout.spring.service.exceptions.services;

public class CatServiceException extends Exception {
    private CatServiceException(String message) {
        super(message);
    }

    public static CatServiceException createInvalidBirthDate() {
        return new CatServiceException("Birth date is invalid");
    }
}
