package org.forumflow.backend.user.infrastructure.model.response;

public record UserResponse(
        String username,
        UserDetailResponse userDetail
) {
}
