package org.forumflow.backend.user.infraestructure.api;

import org.forumflow.backend.user.application.business.UserBusiness;
import org.forumflow.backend.user.infraestructure.model.request.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserBusiness userBusiness;

    public UserController(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@Validated @RequestBody ChangePasswordRequest request, Principal connectedUser) {
        userBusiness.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
