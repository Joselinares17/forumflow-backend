package org.forumflow.backend.user.infrastructure.exception.custom.security;

public class InvalidRoleException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Invalid role";

    public InvalidRoleException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidRoleException(String message) {
        super(message);
    }
}
