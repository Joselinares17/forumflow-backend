package org.forumflow.backend.user.infraestructure.api;

import org.forumflow.backend.user.application.business.ModeratorBusiness;
import org.forumflow.backend.user.infraestructure.model.response.ModerationResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/moderators")
public class ModeratorController {
    private final ModeratorBusiness moderatorBusiness;

    public ModeratorController(ModeratorBusiness moderatorBusiness) {
        this.moderatorBusiness = moderatorBusiness;
    }

    @PostMapping("/{id}/suspend-temporarily")
    @Secured("ROLE_USER")
    public ResponseEntity<ModerationResultResponse> suspendTemporarily(@PathVariable Long id, @RequestParam String duration) {
        return ResponseEntity.ok(moderatorBusiness.suspendUserTemporarily(id, Duration.parse(duration.trim())));
    }

    @PostMapping("/{id}/suspend-permanently")
    @Secured("ROLE_MODERATOR")
    public ResponseEntity<ModerationResultResponse> suspendPermanently(@PathVariable Long id) {
        return ResponseEntity.ok(moderatorBusiness.suspendUserPermanently(id));
    }

    @PostMapping("/{id}/unsuspend")
    @Secured("ROLE_USER")
    public ResponseEntity<ModerationResultResponse> unsuspend(@PathVariable Long id) {
        return ResponseEntity.ok(moderatorBusiness.unsuspendUser(id));
    }

    @PostMapping("/{id}/ban-temporarily")
    @Secured("ROLE_MODERATOR")
    public ResponseEntity<ModerationResultResponse> banTemporarily(@PathVariable Long id, @RequestBody String duration) {
        return ResponseEntity.ok(moderatorBusiness.banUserTemporarily(id, Duration.parse(duration)));
    }

    @PostMapping("/{id}/ban-permanently")
    @Secured("ROLE_MODERATOR")
    public ResponseEntity<ModerationResultResponse> banPermanently(@PathVariable Long id) {
        return ResponseEntity.ok(moderatorBusiness.banUserPermanently(id));
    }

    @PostMapping("/{id}/unban")
    @Secured("ROLE_MODERATOR")
    public ResponseEntity<ModerationResultResponse> unban(@PathVariable Long id) {
        return ResponseEntity.ok(moderatorBusiness.unbanUser(id));
    }

    @GetMapping
    @Secured("ROLE_MODERATOR")
    public ResponseEntity<?> getHelloModerator() {
        return ResponseEntity.ok("Hello from moderator.");
    }
}
