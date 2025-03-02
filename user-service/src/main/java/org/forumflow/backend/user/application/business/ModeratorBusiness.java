package org.forumflow.backend.user.application.business;

import org.forumflow.backend.user.application.service.IModeratorService;
import org.forumflow.backend.user.domain.entity.TypeRole;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.RoleRepository;
import org.forumflow.backend.user.domain.repository.TokenRepository;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.forumflow.backend.user.infrastructure.exception.custom.security.DatabaseOperationException;
import org.forumflow.backend.user.infrastructure.exception.custom.security.UnauthorizedActionException;
import org.forumflow.backend.user.infrastructure.exception.custom.user.UserNotFoundException;
import org.forumflow.backend.user.infrastructure.model.response.ModerationResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ModeratorBusiness implements IModeratorService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;

    private static final Logger log = LoggerFactory.getLogger(ModeratorBusiness.class);

    public ModeratorBusiness(UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public ModerationResultResponse suspendUserTemporarily(Long id, Duration duration) {
        log.error("timer in minutes is {}: ", duration.toMinutes());
        return handleSuspension(id, true, duration);
    }

    @Override
    @Transactional
    public ModerationResultResponse suspendUserPermanently(Long id) {
        return handleSuspension(id, true, null); // duration null indica permanente
    }

    @Override
    @Transactional
    public ModerationResultResponse unsuspendUser(Long id) {
        return handleSuspension(id, false, null);
    }

    @Override
    @Transactional
    public ModerationResultResponse banUserTemporarily(Long id, Duration duration) {
        return handleBan(id, true, duration);
    }

    @Override
    @Transactional
    public ModerationResultResponse banUserPermanently(Long id) {
        return handleBan(id, true, null); // duration null indica permanente
    }

    @Override
    @Transactional
    public ModerationResultResponse unbanUser(Long id) {
        User userDb = getUserById(id);

        if (userDb.isEnabled()) {
            throw new IllegalStateException("User is already activated");
        }

        userDb.setEnabled(true);
        return saveUserAndReturnResult(userDb, id, "Reactivate", null);
    }

    private User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        if (roleRepository.hasRoleAdminById(TypeRole.ADMIN.getValue(), id)) {
            throw new UnauthorizedActionException();
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    private ModerationResultResponse saveUserAndReturnResult(User user, Long id, String action, Duration duration) {
        try {
            userRepository.save(user); // Guardamos el usuario en la base de datos
            log.info("User {} {} successfully", id, action);

            return ModerationResultResponse.builder()
                    .success(true)
                    .action(action)
                    .userId(id)
                    .duration(duration)
                    .build();
        } catch (RuntimeException e) {
            log.error("Database error while {} user {}: {}", action, id, e.getMessage());
            throw new DatabaseOperationException("Failed to modify user due to data integrity violation", e);
        }
    }

    // Para suspensiones (locked)
    private ModerationResultResponse handleSuspension(Long id, boolean suspend, Duration duration) {
        User userDb = getUserById(id);

        if (suspend && !userDb.isAccountNonLocked()) {
            throw new IllegalStateException("User is already suspended");
        }

        if (suspend) {
            userDb.setSuspensionStart(LocalDateTime.now());
            userDb.setSuspensionDuration(duration);
            userDb.setAccountNonLocked(false);
            tokenRepository.deleteAllByUserId(id);
        } else {
            userDb.setSuspensionStart(null);
            userDb.setSuspensionDuration(null);
            userDb.setAccountNonLocked(true);
        }

        return saveUserAndReturnResult(userDb, id, suspend ? "Suspend" : "Unsuspend", duration);
    }

    // Para baneos (enabled)
    private ModerationResultResponse handleBan(Long id, boolean ban, Duration duration) {
        User userDb = getUserById(id);

        if (ban && !userDb.isEnabled()) {
            throw new IllegalStateException("User is already banned");
        }

        if (ban) {
            userDb.setBanStart(LocalDateTime.now());
            userDb.setBanDuration(duration);
            userDb.setEnabled(false);

        } else {
            userDb.setBanStart(null);
            userDb.setBanDuration(null);
            userDb.setEnabled(true);
        }

        return saveUserAndReturnResult(userDb, id, ban ? "Ban" : "Unban", duration);
    }
}
