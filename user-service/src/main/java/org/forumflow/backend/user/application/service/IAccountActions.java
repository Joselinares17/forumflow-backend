package org.forumflow.backend.user.application.service;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.infrastructure.model.response.ModerationResultResponse;

import java.time.Duration;

public interface IAccountActions {
    User getUserById(Long id);
    ModerationResultResponse saveUserAndReturnResult(User user, Long id, String action, Duration duration);
    ModerationResultResponse handleSuspension(Long id, boolean suspend, Duration duration);
    ModerationResultResponse handleBan(Long id, boolean ban, Duration duration);
}
