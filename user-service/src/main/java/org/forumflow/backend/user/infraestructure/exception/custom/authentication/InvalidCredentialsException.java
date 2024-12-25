package org.forumflow.backend.user.infraestructure.exception.custom.authentication;

import org.springframework.security.core.AuthenticationException;

public class InvalidCredentialsException extends AuthenticationException {
    public InvalidCredentialsException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidCredentialsException(String msg) {
        super(msg);
    }
}
