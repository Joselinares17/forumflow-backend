package org.forumflow.backend.user.infraestructure.api;

import org.forumflow.backend.user.application.business.UserBusiness;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.infraestructure.model.request.ChangePasswordRequest;
import org.forumflow.backend.user.infraestructure.model.request.UpdateInfoRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
//TODO: Realizar un refartor a los mappingsMethods
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

    @GetMapping("/{username}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userBusiness.getUserInfoByUsername(username));
    }

    @GetMapping
    @Secured("ROLE_USER")
    public ResponseEntity<?> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(userBusiness.getAllUsersWithDetails(pageable));
    }

    @DeleteMapping
    @Secured("ROLE_USER")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user) {
        userBusiness.deleteUser(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
