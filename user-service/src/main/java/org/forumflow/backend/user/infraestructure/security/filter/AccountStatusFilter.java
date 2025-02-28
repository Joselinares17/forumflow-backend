package org.forumflow.backend.user.infraestructure.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.forumflow.backend.user.infraestructure.security.service.IUserService;
import org.forumflow.backend.user.infraestructure.security.service.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AccountStatusFilter extends OncePerRequestFilter {
    private final IUserService userService;
    private final JwtService jwtService;

    public AccountStatusFilter(@Qualifier("cache") IUserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        //TODO: Implementar algo xd
        /*
        if (request.getServletPath().equals("/api/v1/auth/register") ||
                request.getServletPath().equals("/api/v1/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);

        if (username == null || username.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        if (userService.existsUserByUsername(username)) {
            try {
                userService.saveUserChecked(username);
            } catch (RuntimeException e) {
                // Manejar excepciones de cuenta suspendida o baneada
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Account status issue: " + e.getMessage());
                return;
            }
        }
        */

        filterChain.doFilter(request, response);
    }
}
