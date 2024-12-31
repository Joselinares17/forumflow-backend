package org.forumflow.backend.user.infraestructure.model.response;

public record UserDetailResponse(
        String firstName,
        String lastName,
        String email
) {
}
