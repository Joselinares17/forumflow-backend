package org.forumflow.backend.user.infraestructure.security.service;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
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

    private Optional<User> checkAccountStatus(User user) {
        // Verificar suspensión
        if (!user.isAccountNonLocked() && user.getSuspensionStart() != null && user.getSuspensionDuration() != null) {
            LocalDateTime suspensionEnd = user.getSuspensionStart().plus(user.getSuspensionDuration());
            if (LocalDateTime.now().isAfter(suspensionEnd)) {
                user.setAccountNonLocked(true);
                user.setSuspensionStart(null);
                user.setSuspensionDuration(null);
                updateContextSecurity(user);
                return Optional.of(user);
            }
        }

        // Verificar baneo
        if (!user.isEnabled() && user.getBanStart() != null && user.getBanDuration() != null) {
            LocalDateTime banEnd = user.getBanStart().plus(user.getBanDuration());
            if (LocalDateTime.now().isAfter(banEnd)) {
                user.setEnabled(true);
                user.setBanStart(null);
                user.setBanDuration(null);
                updateContextSecurity(user);
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    private void updateContextSecurity(User user) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth != null && currentAuth.getName().equals(user.getUsername())) {
            UsernamePasswordAuthenticationToken updatedAuth = new UsernamePasswordAuthenticationToken(
                    user,
                    null, // Aquí generalmente se pasa las credenciales, pero si es solo para la autenticación inicial puedes usar null
                    user.getAuthorities() // Asigna los roles o permisos del usuario
            );

            // Establecer el nuevo contexto de seguridad para el usuario recién autenticado
            SecurityContextHolder.getContext().setAuthentication(updatedAuth);
        }
    }
}
