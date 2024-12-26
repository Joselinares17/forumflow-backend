package org.forumflow.backend.user.infraestructure.exception.handler;

import org.forumflow.backend.user.infraestructure.exception.custom.authentication.ExpiredSessionException;
import org.forumflow.backend.user.infraestructure.exception.custom.authentication.InvalidCredentialsException;
import org.forumflow.backend.user.infraestructure.exception.custom.authentication.InvalidTokenException;
import org.forumflow.backend.user.infraestructure.exception.custom.security.InsufficientPermissionsException;
import org.forumflow.backend.user.infraestructure.exception.custom.security.InvalidRoleException;
import org.forumflow.backend.user.infraestructure.exception.custom.security.PermissionDeniedException;
import org.forumflow.backend.user.infraestructure.exception.custom.security.RoleAssignmentException;
import org.forumflow.backend.user.infraestructure.exception.custom.security.RoleModificationNotAllowedException;
import org.forumflow.backend.user.infraestructure.exception.custom.security.RoleNotFoundException;
import org.forumflow.backend.user.infraestructure.exception.custom.security.UnauthorizedActionException;
import org.forumflow.backend.user.infraestructure.exception.custom.user.InvalidUserInputException;
import org.forumflow.backend.user.infraestructure.exception.custom.user.UserAlreadyExistsException;
import org.forumflow.backend.user.infraestructure.exception.custom.user.UserNotFoundException;
import org.forumflow.backend.user.infraestructure.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Validation errors: {}", errors);
        return new ErrorResponse(
                "VALIDATION_ERROR",
                errors.toString(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication error: {}", ex.getMessage());
        return new ErrorResponse(
                "AUTHENTICATION_ERROR",
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
    }

    @ExceptionHandler({
            InvalidCredentialsException.class,
            InvalidTokenException.class,
            ExpiredSessionException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleSpecificAuthErrors(Exception ex) {
        log.error("Specific authentication error: {}", ex.getMessage());
        return new ErrorResponse(
                "AUTH_ERROR",
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            InvalidUserInputException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserErrors(Exception ex) {
        log.error("User error: {}", ex.getMessage());
        return new ErrorResponse(
                "USER_ERROR",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserConflict(UserAlreadyExistsException ex) {
        log.error("User conflict: {}", ex.getMessage());
        return new ErrorResponse(
                "USER_CONFLICT",
                ex.getMessage(),
                HttpStatus.CONFLICT.value()
        );
    }

    @ExceptionHandler({
            PermissionDeniedException.class,
            InsufficientPermissionsException.class,
            UnauthorizedActionException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlePermissionErrors(Exception ex) {
        log.error("Permission error: {}", ex.getMessage());
        return new ErrorResponse(
                "PERMISSION_ERROR",
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value()
        );
    }

    @ExceptionHandler({
            RoleNotFoundException.class,
            InvalidRoleException.class,
            RoleAssignmentException.class,
            RoleModificationNotAllowedException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRoleErrors(Exception ex) {
        log.error("Role error: {}", ex.getMessage());
        return new ErrorResponse(
                "ROLE_ERROR",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericError(Exception ex) {
        log.error("Unexpected error: ", ex);
        return new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}
