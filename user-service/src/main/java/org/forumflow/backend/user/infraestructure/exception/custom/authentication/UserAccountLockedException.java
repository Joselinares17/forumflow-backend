package org.forumflow.backend.user.infraestructure.exception.custom.authentication;

public class UserAccountLockedException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "User account locked";

    public UserAccountLockedException() {
        super(DEFAULT_MESSAGE);
    }

    public UserAccountLockedException(String message) {
        super(message);
    }
}
