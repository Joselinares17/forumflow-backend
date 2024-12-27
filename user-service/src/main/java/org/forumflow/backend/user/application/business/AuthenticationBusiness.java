package org.forumflow.backend.user.application.business;

import jakarta.servlet.http.HttpServletRequest;
import org.forumflow.backend.user.application.service.IAuthenticateService;
import org.forumflow.backend.user.domain.entity.Role;
import org.forumflow.backend.user.domain.entity.Token;
import org.forumflow.backend.user.domain.entity.TypeRole;
import org.forumflow.backend.user.domain.entity.TypeToken;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.entity.UserDetail;
import org.forumflow.backend.user.domain.repository.RoleRepository;
import org.forumflow.backend.user.domain.repository.TokenRepository;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.forumflow.backend.user.infraestructure.exception.custom.authentication.InvalidTokenException;
import org.forumflow.backend.user.infraestructure.exception.custom.security.RoleNotFoundException;
import org.forumflow.backend.user.infraestructure.exception.custom.security.UnauthorizedActionException;
import org.forumflow.backend.user.infraestructure.exception.custom.user.UserAlreadyExistsException;
import org.forumflow.backend.user.infraestructure.exception.custom.user.UserNotFoundException;
import org.forumflow.backend.user.infraestructure.model.request.AuthenticationRequest;
import org.forumflow.backend.user.infraestructure.model.request.RegisterRequest;
import org.forumflow.backend.user.infraestructure.model.response.AuthenticationResponse;
import org.forumflow.backend.user.infraestructure.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationBusiness(UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        Role userRole = roleRepository.findByTypeRole(TypeRole.USER)
                .orElseThrow(RoleNotFoundException::new);

        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("email is already in use");
        }

        List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        UserDetail userDetail = UserDetail.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .createdAt(LocalDateTime.now())
                .build();

        User user = User.builder()
                .userDetail(userDetail)
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(roles)
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(UserNotFoundException::new);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .typeToken(TypeToken.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            throw new InvalidTokenException("Invalid tokens by user");
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(request.getUserPrincipal() == null){
            throw new UnauthorizedActionException("Unauthorized action");
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid token by headers");
        }

        String refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new InvalidTokenException("Invalid token by username");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new InvalidTokenException("Invalid token");
        }

        String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(jwtService.generateRefreshToken(user)) // Genera un nuevo refreshToken
                .build();
    }
}
