package org.forumflow.backend.user.infrastructure.security.service;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Primary
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void saveUserChecked(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Optional<User> userChecked = checkAccountStatus(user);

        if (userChecked.isEmpty()) {
            throw new RuntimeException("Account is still suspended or locked.");
        }

        userRepository.save(userChecked.get());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
