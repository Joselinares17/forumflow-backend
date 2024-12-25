package org.forumflow.backend.user.infraestructure.model.request;

public record AuthenticationRequest(
        String username,
        String password) {
}
