package org.forumflow.backend.user.infraestructure.api;

import org.forumflow.backend.user.application.business.UserBusiness;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.infraestructure.model.request.ChangePasswordRequest;
import org.forumflow.backend.user.infraestructure.model.request.UpdateInfoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserBusiness userBusiness;

    public UserController(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @PatchMapping("/change-password")
    @Secured("ROLE_USER")
    public ResponseEntity<?> changePassword(@Validated @RequestBody ChangePasswordRequest request,
                                            @AuthenticationPrincipal User user) {
        userBusiness.changePassword(request, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-info")
    @Secured("ROLE_USER")
    public ResponseEntity<?> updateInfo(@Validated @RequestBody UpdateInfoRequest request,
                                        @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userBusiness.updateUserInfo(request, user));
    }
}
