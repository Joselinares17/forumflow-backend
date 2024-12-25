package org.forumflow.backend.user.infraestructure.exception.custom.authentication;

import org.springframework.security.core.AuthenticationException;

public class ExpiredSessionException extends AuthenticationException {
    public ExpiredSessionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ExpiredSessionException(String msg) {
        super(msg);
    }
}
