package org.forumflow.backend.user.infraestructure.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotBlank(message = "current password cannot be blank")
    private String currentPassword;
    @NotBlank(message = "new password cannot be blank")
    @Size(min = 4, max = 200, message = "new password cannot be less than 4 characters")
    private String newPassword;
    @NotBlank(message = "confirmation password cannot be blank")
    @Size(min = 4, max = 200, message = "confirmation password cannot be less than 4 characters")
    private String confirmationPassword;
}
