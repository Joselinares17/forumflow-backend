package org.forumflow.backend.user.infraestructure.exception.custom.security;

public class RoleAssignmentException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Role assignment error";

    public RoleAssignmentException() {
        super(DEFAULT_MESSAGE);
    }

    public RoleAssignmentException(String message) {
        super(message);
    }
}
