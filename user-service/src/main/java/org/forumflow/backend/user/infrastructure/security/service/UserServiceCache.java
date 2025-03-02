package org.forumflow.backend.user.infrastructure.security.service;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("cache")
public class UserServiceCache implements IUserService {
    private final RedisService redisService;
    private final UserRepository userRepository;

    public UserServiceCache(RedisService redisService, UserRepository userRepository) {
        this.redisService = redisService;
        this.userRepository = userRepository;
    }

    @Override
    public void saveUserChecked(String username) {
        if(!redisService.exists(username)) {
            throw new UsernameNotFoundException("User not found in caché");
        }

        User user = (User) redisService.get(username);
        Optional<User> userOptional = checkAccountStatus(user);

        redisService.save(username, userOptional.get());
    }

    @Override
    public boolean existsUserByUsername(String username) {
        return redisService.exists(username);
    }
}
