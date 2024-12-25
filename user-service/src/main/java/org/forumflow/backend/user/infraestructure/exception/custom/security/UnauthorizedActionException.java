package org.forumflow.backend.user.infraestructure.exception.custom.security;

public class UnauthorizedActionException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Unauthorized action";

    public UnauthorizedActionException() {
        super(DEFAULT_MESSAGE);
    }

    public UnauthorizedActionException(String message) {
        super(message);
    }
}
