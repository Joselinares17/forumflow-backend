package org.forumflow.backend.user.application.business;

import org.forumflow.backend.user.application.service.IModeratorService;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.forumflow.backend.user.infraestructure.exception.custom.user.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ModeratorBusiness implements IModeratorService {
    private final UserRepository userRepository;

    public ModeratorBusiness(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean suspendUserTemporarily(Long id, Duration duration) {
        return false;
    }

    @Override
    public boolean suspendUserPermanently(Long id) {
        return false;
    }

    @Override
    public boolean unsuspendUser(Long id) {
        return false;
    }

    @Override
    public boolean desactivateUser(Long id) {
        return false;
    }

    @Override
    public boolean reactivateUser(Long id) {
        return false;
    }

    @Override
    public boolean banUserTemporary(Long id, Duration duration) {
        return false;
    }

    @Override
    public boolean banUserPermanently(Long id) {
        return false;
    }
}
