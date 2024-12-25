package org.forumflow.backend.user.infraestructure.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank(message = "current password cannot be blank")
        String currentPassword,
        @NotBlank(message = "new password cannot be blank")
            @Size(min = 4, max = 200, message = "new password cannot be less than 4 characters")
        String newPassword,
        @NotBlank(message = "confirmation password cannot be blank")
            @Size(min = 4, max = 200, message = "confirmation password cannot be less than 4 characters")
        String confirmationPassword
) {    
}
