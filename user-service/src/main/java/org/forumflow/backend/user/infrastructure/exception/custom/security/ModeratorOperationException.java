package org.forumflow.backend.user.infrastructure.exception.custom.security;

public class ModeratorOperationException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Moderator operation fail.";

    public ModeratorOperationException() {
        super(DEFAULT_MESSAGE);
    }

    public ModeratorOperationException(String message) {
        super(message);
    }

    public ModeratorOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
