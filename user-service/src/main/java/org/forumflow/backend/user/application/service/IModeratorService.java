package org.forumflow.backend.user.application.service;

import org.forumflow.backend.user.infrastructure.model.response.ModerationResultResponse;

import java.time.Duration;

public interface IModeratorService {
    ModerationResultResponse suspendUserTemporarily(Long id, Duration duration);

    ModerationResultResponse suspendUserPermanently(Long id);

    ModerationResultResponse unsuspendUser(Long id);

    ModerationResultResponse banUserTemporarily(Long id, Duration duration);

    ModerationResultResponse banUserPermanently(Long id);

    ModerationResultResponse unbanUser(Long id);
}
