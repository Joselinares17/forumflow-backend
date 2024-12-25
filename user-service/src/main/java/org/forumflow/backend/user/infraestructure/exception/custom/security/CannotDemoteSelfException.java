package org.forumflow.backend.user.infraestructure.exception.custom.security;

public class CannotDemoteSelfException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Cannot demote self role";

    public CannotDemoteSelfException() {
        super(DEFAULT_MESSAGE);
    }

    public CannotDemoteSelfException(String message) {
        super(message);
    }
}
