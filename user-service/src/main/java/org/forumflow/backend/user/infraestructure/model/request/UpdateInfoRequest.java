package org.forumflow.backend.user.infraestructure.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateInfoRequest(
        @NotBlank(message = "this cannot be empty")
            @Size(min = 3, max = 20, message = "must be between 3 and 20 characters")
        String firstname,
        @NotBlank(message = "this cannot be empty")
            @Size(min = 3, max = 20, message = "must be between 3 and 20 characters")
        String lastname,
        @NotBlank(message = "this cannot be empty")
           @Email(message = "must be a valid email")
        String email
) {
}
