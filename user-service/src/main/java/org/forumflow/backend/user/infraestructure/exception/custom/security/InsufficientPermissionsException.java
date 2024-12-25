package org.forumflow.backend.user.infraestructure.exception.custom.security;

public class InsufficientPermissionsException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Insufficient permissions";

    public InsufficientPermissionsException() {
        super(DEFAULT_MESSAGE);
    }

    public InsufficientPermissionsException(String message) {
        super(message);
    }
}
