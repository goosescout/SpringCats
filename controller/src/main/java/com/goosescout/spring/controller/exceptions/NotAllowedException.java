package com.goosescout.spring.controller.exceptions;

public class NotAllowedException extends Exception {
    private NotAllowedException(String message) {
        super(message);
    }

    public static NotAllowedException create(String message) {
        return new NotAllowedException(message);
    }
}
