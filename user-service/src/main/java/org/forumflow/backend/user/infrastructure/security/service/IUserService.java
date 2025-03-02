package org.forumflow.backend.user.infrastructure.security.service;

import org.forumflow.backend.user.domain.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IUserService {
    void saveUserChecked(String username);
    boolean existsUserByUsername(String username);

    default Optional<User> checkAccountStatus(User user) {
        // Verificar suspensión
        if (!user.isAccountNonLocked() && user.getSuspensionStart() != null && user.getSuspensionDuration() != null) {
            LocalDateTime suspensionEnd = user.getSuspensionStart().plus(user.getSuspensionDuration());
            if (LocalDateTime.now().isAfter(suspensionEnd)) {
                user.setAccountNonLocked(true);
                user.setSuspensionStart(null);
                user.setSuspensionDuration(null);
                updateContextSecurity(user);
                return Optional.of(user);
            } else {
                throw new RuntimeException("Account is suspended.");
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
            } else {
                throw new RuntimeException("Account is banned.");
            }
        }

        return Optional.empty();
    }
    default void updateContextSecurity(User user) {
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
