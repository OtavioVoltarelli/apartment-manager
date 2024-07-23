package com.apartment_manager.exceptions;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException() {
        super("Object not found");
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
