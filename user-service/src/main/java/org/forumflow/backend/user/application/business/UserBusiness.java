package org.forumflow.backend.user.application.business;

import org.forumflow.backend.user.application.service.IUserService;
import org.forumflow.backend.user.domain.entity.TypeRole;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.TokenRepository;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.forumflow.backend.user.infraestructure.exception.custom.security.PermissionDeniedException;
import org.forumflow.backend.user.infraestructure.exception.custom.user.UserNotFoundException;
import org.forumflow.backend.user.infraestructure.model.mapper.UserMapper;
import org.forumflow.backend.user.infraestructure.model.request.ChangePasswordRequest;
import org.forumflow.backend.user.infraestructure.model.request.UpdateInfoRequest;
import org.forumflow.backend.user.infraestructure.model.response.UserDetailResponse;
import org.forumflow.backend.user.infraestructure.model.response.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;

@Service
public class UserBusiness implements IUserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private static final Logger log = LoggerFactory.getLogger(UserBusiness.class);

    public UserBusiness(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request, User user) {
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetailResponse updateUserInfo(UpdateInfoRequest request, User user) {
        User userSaved = userRepository.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);

        userSaved.getUserDetail().setFirstname(request.firstname());
        userSaved.getUserDetail().setLastname(request.lastname());
        userSaved.getUserDetail().setEmail(request.email());

        log.error("Validation details: {}", userSaved.getUserDetail().toString());

        return userMapper.toUserDetailResponse(userSaved.getUserDetail());
    }

    //TODO: Reemplazar esta búsqueda por alternativas dinámicas y completas
    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfoByUsername(String username) {
        User userSaved = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return userMapper.toUserResponse(userSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsersWithDetails(Pageable pageable) {
        return userRepository.findAllUsersWithDetails(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()))
                .map(userMapper::toUserResponse);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        if (!roles.contains("ROLE_USER")) {
            throw new PermissionDeniedException();
        }

        tokenRepository.deleteAllByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }
}
