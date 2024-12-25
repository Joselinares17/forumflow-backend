package org.forumflow.backend.user.infraestructure.exception.custom.security;

public class UserNotAuthorizedToManageRoleException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "User not authorized to manage role";

    public UserNotAuthorizedToManageRoleException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotAuthorizedToManageRoleException(String message) {
        super(message);
    }
}
