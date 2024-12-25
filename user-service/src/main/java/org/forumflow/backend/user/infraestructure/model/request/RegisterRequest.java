package org.forumflow.backend.user.infraestructure.model.request;

public record RegisterRequest(
        String firstname,
        String lastname,
        String username,
        String email,
        String password
) {
}
