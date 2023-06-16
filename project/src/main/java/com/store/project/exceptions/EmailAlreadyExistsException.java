package com.store.project.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EmailAlreadyExistsException(String email) {
        super(String.format("An account with this email: %s already exists",email));
    }
}
