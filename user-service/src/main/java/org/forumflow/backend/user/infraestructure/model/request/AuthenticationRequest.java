package org.forumflow.backend.user.infraestructure.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 4, max = 50, message = "Password must be between 4 and 50 characters")
        String password
) {
}
