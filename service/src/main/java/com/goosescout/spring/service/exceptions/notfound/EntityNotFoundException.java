package com.goosescout.spring.service.exceptions.notfound;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException createWithId(String entityName, Long id) {
        return new EntityNotFoundException(String.format("%s with id %d was not found", entityName, id));
    }

    public static EntityNotFoundException createWithUsername(String entityName, String username) {
        return new EntityNotFoundException(String.format("%s with username %s was not found", entityName, username));
    }
}
