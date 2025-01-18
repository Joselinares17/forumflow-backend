package org.forumflow.backend.user.infraestructure.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.forumflow.backend.user.infraestructure.model.request.AuthenticationRequest;
import org.forumflow.backend.user.infraestructure.security.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AccountStatusFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public AccountStatusFilter(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        //TODO: Implementar lógica para verificar estado de cuenta desde caché.
        if (!request.getServletPath().equals("api/v1/auth/authenticate")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getMethod().equalsIgnoreCase("POST")) {
            AuthenticationRequest authenticationRequest = objectMapper.readValue(request.getInputStream(), AuthenticationRequest.class);
            String username = authenticationRequest.username();

            if (username == null || username.isEmpty()) {
                throw new RuntimeException("Username is empty.");
            }

            if (!userService.existsUserByUsername(username)) {
                filterChain.doFilter(request, response);
                return;
            }

            userService.loadUserChecked(username, request);
        }

        filterChain.doFilter(request, response);
    }
}
