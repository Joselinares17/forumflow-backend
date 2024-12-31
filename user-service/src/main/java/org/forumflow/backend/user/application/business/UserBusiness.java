package org.forumflow.backend.user.application.business;

import org.forumflow.backend.user.application.service.IUserService;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.forumflow.backend.user.infraestructure.exception.custom.user.UserNotFoundException;
import org.forumflow.backend.user.infraestructure.model.request.ChangePasswordRequest;
import org.forumflow.backend.user.infraestructure.model.request.UpdateInfoRequest;
import org.forumflow.backend.user.infraestructure.model.response.UserDetailResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBusiness implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserBusiness(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //TODO: Mixear lógica para modificar todos los atributos del usuario en un solo método.
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

        return new UserDetailResponse(
                request.firstname(),
                request.lastname(),
                request.email()
        );
    }
}
