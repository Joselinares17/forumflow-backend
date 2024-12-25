package org.forumflow.backend.user.infraestructure.exception.custom.security;

public class PermissionDeniedException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Permission denied.";

    public PermissionDeniedException() {
        super(DEFAULT_MESSAGE);
    }

    public PermissionDeniedException(String message) {
        super(message);
    }
}
