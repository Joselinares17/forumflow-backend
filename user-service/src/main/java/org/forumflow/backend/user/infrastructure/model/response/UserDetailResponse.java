package org.forumflow.backend.user.infrastructure.model.response;

public record UserDetailResponse(
        String firstname,
        String lastname,
        String email
) {
}
