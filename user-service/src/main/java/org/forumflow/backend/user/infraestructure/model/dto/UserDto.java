package org.forumflow.backend.user.infraestructure.model.dto;

public record UserDto(
        Long id,
        String username,
        String password
) {
}
