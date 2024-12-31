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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Validation errors: {}", errors);
        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                errors,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoHandlerFoundException ex) {
        log.error("Not found error: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "NOT_FOUND",
                body,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication error: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "AUTHENTICATION_ERROR",
                body,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            InvalidCredentialsException.class,
            InvalidTokenException.class,
            ExpiredSessionException.class
    })
    public ResponseEntity<ErrorResponse> handleSpecificAuthErrors(Exception ex) {
        log.error("Specific authentication error: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "AUTH_ERROR",
                body,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            UsernameNotFoundException.class,
            InvalidUserInputException.class
    })
    public ResponseEntity<ErrorResponse> handleUserErrors(Exception ex) {
        log.error("User error: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "USER_ERROR",
                body,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserConflict(UserAlreadyExistsException ex) {
        log.error("User conflict: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "USER_CONFLICT",
                body,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            PermissionDeniedException.class,
            InsufficientPermissionsException.class,
            UnauthorizedActionException.class
    })
    public ResponseEntity<ErrorResponse> handlePermissionErrors(Exception ex) {
        log.error("Permission error: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "PERMISSION_ERROR",
                body,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            RoleNotFoundException.class,
            InvalidRoleException.class,
            RoleAssignmentException.class,
            RoleModificationNotAllowedException.class
    })
    public ResponseEntity<ErrorResponse> handleRoleErrors(Exception ex) {
        log.error("Role error: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "ROLE_ERROR",
                body,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Illegal argument error: {}", ex.getMessage());
        Map<String, String> body = new HashMap<>();
        body.put("message", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "SQL_ERROR",
                body,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericError(Exception ex) {
        log.error("Unexpected error: ", ex);
        Map<String, String> body = new HashMap<>();
        body.put("message", "An unexpected error occurred");
        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                body,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}