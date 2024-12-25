package org.forumflow.backend.user.infraestructure.exception.custom.security;

public class RoleNotFoundException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Role not found.";

    public RoleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}
