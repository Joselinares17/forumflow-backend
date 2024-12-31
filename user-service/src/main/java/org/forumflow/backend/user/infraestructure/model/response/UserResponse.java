package org.forumflow.backend.user.infraestructure.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        String username,
        UserDetailResponse userDetailResponse
) {
}
