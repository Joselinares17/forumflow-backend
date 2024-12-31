package org.forumflow.backend.user.infraestructure.model.response;

public record UserDetailResponse(
        String firstname,
        String lastname,
        String email
) {
}
