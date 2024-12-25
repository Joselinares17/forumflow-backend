package org.forumflow.backend.user.infraestructure.model.response;

public record ErrorResponse(
        String code,
        String message,
        int status
) {
}
