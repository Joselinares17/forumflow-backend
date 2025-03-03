package org.forumflow.backend.user.infrastructure.model.response;

public record AdminActionResponse(
        boolean status,
        String description
) {
}
