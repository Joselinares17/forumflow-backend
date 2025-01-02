package org.forumflow.backend.user.infraestructure.security;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isCurrentlySuspended(String username) {
        return this.userRepository.findByUsername(username)
                .map(User::isCurrentlySuspended)
                .orElse(false);
    }

    public boolean isCurrentlyBanned(String username) {
        return this.userRepository.findByUsername(username)
                .map(User::isCurrentlyBanned)
                .orElse(false);
    }
}
