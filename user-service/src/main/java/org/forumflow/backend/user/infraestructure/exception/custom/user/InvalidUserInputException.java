package org.forumflow.backend.user.infraestructure.exception.custom.user;

public class InvalidUserInputException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Invalid user input.";

    public InvalidUserInputException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidUserInputException(String message) {
        super(message);
    }
}
