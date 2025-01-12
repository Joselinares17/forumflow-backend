package org.forumflow.backend.user.infraestructure.security;

import jakarta.servlet.http.HttpServletRequest;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
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

    @Transactional
    public void loadUserChecked(String username, HttpServletRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Optional<User> userChecked = checkAccountStatus(user, request);

        if (userChecked.isEmpty()) {
            throw new RuntimeException("Account is still suspended or locked.");
        }

        userRepository.save(userChecked.get());
    }

    @Transactional(readOnly = true)
    public boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private Optional<User> checkAccountStatus(User user, HttpServletRequest request) {
        // Verificar si la cuenta está suspendida y si la suspensión ya expiró
        if (!user.isAccountNonLocked() && user.getSuspensionStart() != null && user.getSuspensionDuration() != null) {
            LocalDateTime suspensionEnd = user.getSuspensionStart().plus(user.getSuspensionDuration());
            if (LocalDateTime.now().isAfter(suspensionEnd)) {
                user.setAccountNonLocked(true);
                user.setSuspensionStart(null);
                user.setSuspensionDuration(null);
                updateContextSecurity(user, request);
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    private void updateContextSecurity(User user, HttpServletRequest request) {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth != null && currentAuth.getName().equals(user.getUsername())) {
            UsernamePasswordAuthenticationToken updatedAuth = new UsernamePasswordAuthenticationToken(
                    user,
                    null, // Aquí generalmente se pasa las credenciales, pero si es solo para la autenticación inicial puedes usar null
                    user.getAuthorities() // Asigna los roles o permisos del usuario
            );

            // Establecer detalles adicionales del contexto de seguridad si es necesario
            updatedAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Establecer el nuevo contexto de seguridad para el usuario recién autenticado
            SecurityContextHolder.getContext().setAuthentication(updatedAuth);
        }
    }
}
