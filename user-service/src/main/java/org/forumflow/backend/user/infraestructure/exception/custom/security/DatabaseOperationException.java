package org.forumflow.backend.user.infraestructure.exception.custom.security;

public class DatabaseOperationException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Database operation fail.";

    public DatabaseOperationException() {
        super(DEFAULT_MESSAGE);
    }

    public DatabaseOperationException(String message) {
        super(message);
    }

    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
