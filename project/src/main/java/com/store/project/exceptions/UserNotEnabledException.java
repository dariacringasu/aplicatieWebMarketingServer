package com.store.project.exceptions;

public class UserNotEnabledException extends  RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotEnabledException(String email) {
        super(String.format("The account with this email: %s was not enabled",email));
    }
}
