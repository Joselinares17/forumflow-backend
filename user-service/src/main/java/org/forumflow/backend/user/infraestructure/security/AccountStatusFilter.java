package org.forumflow.backend.user.infraestructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AccountStatusFilter extends OncePerRequestFilter {
    private final UserService userService;

    public AccountStatusFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();

        if (userService.isCurrentlySuspended(username)) {
            response.setStatus(403);
            response.getWriter().write("Account suspended");
            return;
        }

        if (userService.isCurrentlyBanned(username)) {
            response.setStatus(403);
            response.getWriter().write("Account banned");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
