package org.forumflow.backend.user.infraestructure.model.response;

public record UserResponse(
        String username,
        UserDetailResponse userDetail
) {
}
