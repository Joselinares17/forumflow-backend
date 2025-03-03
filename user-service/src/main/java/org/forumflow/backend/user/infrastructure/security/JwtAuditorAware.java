package org.forumflow.backend.user.infrastructure.security;

import org.forumflow.backend.user.domain.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class JwtAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("SYSTEM");
        }

        //TODO: Confirmar que el método sea una instancia del tipo user
        if(authentication.getPrincipal() instanceof User) {
            return Optional.of(((User) authentication.getPrincipal()).getUsername());
        }

        //TODO: Revisar si se puede obtener el nombre desde una autenticación
        return Optional.of(authentication.getName());
    }
}
