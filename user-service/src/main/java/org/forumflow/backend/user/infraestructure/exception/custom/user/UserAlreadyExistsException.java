package org.forumflow.backend.user.infraestructure.exception.custom.user;

public class UserAlreadyExistsException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "User already exists.";

    public UserAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
