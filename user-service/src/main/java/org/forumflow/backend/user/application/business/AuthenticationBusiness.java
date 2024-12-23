package org.forumflow.backend.user.application.business;

import org.forumflow.backend.user.application.service.IAuthenticateService;
import org.forumflow.backend.user.domain.entity.Role;
import org.forumflow.backend.user.domain.entity.TypeRole;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.entity.UserDetail;
import org.forumflow.backend.user.domain.repository.RoleRepository;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.forumflow.backend.user.infraestructure.model.request.AuthenticationRequest;
import org.forumflow.backend.user.infraestructure.model.request.RegisterRequest;
import org.forumflow.backend.user.infraestructure.model.response.AuthenticationResponse;
import org.forumflow.backend.user.infraestructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationBusiness implements IAuthenticateService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationBusiness(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        Role userRole = roleRepository.findByTypeRole(TypeRole.USER)
                .orElseThrow(RuntimeException::new);

        List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        UserDetail userDetail = UserDetail.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

        User user = User.builder()
                .userDetail(userDetail)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.getUsername(),
                  request.getPassword()
          )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
