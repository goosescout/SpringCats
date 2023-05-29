package com.goosescout.spring.service.exceptions.services;

public class CatOwnerServiceException extends Exception {
    private CatOwnerServiceException(String message) {
        super(message);
    }

    public static CatOwnerServiceException createInvalidBirthDate() {
        return new CatOwnerServiceException("Birth date is invalid");
    }

    public static CatOwnerServiceException createUsernameAlreadyExists(String username) {
        return new CatOwnerServiceException("Username '" + username + "' already exists");
    }
}
