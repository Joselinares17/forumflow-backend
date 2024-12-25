package org.forumflow.backend.user.infraestructure.exception.custom.security;

public class RoleModificationNotAllowedException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Role modification not allowed";

    public RoleModificationNotAllowedException() {
        super(DEFAULT_MESSAGE);
    }

    public RoleModificationNotAllowedException(String message) {
        super(message);
    }
}
