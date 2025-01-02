package org.forumflow.backend.user.application.service;

import java.time.Duration;

public interface IModeratorService {
    boolean suspendUserTemporarily(Long id, Duration duration);

    boolean suspendUserPermanently(Long id);

    boolean unsuspendUser(Long id);

    boolean desactivateUser(Long id);

    boolean reactivateUser(Long id);

    boolean banUserTemporary(Long id, Duration duration);

    boolean banUserPermanently(Long id);
}
