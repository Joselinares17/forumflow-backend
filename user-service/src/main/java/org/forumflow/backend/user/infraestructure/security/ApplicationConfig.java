package org.forumflow.backend.user.infraestructure.security;

import org.forumflow.backend.user.domain.entity.Role;
import org.forumflow.backend.user.domain.entity.TypeRole;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.entity.UserDetail;
import org.forumflow.backend.user.domain.repository.RoleRepository;
import org.forumflow.backend.user.infraestructure.security.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class ApplicationConfig {
    private final UserService userService;

    public ApplicationConfig(UserService userService) {
        this.userService = userService;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return this.userService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.saveAll(List.of(
                        Role.builder().typeRole(TypeRole.USER).build(),
                        Role.builder().typeRole(TypeRole.MODERATOR).build(),
                        Role.builder().typeRole(TypeRole.ADMIN).build()
                ));
            }
            User admin = User.builder()
                    .userDetail(UserDetail.builder()
                            .firstname("Pepe")
                            .lastname("Perez")
                            .email("elpapu@gmail.com")
                            .build()
                    )
                    .username("pepe-admin")
                    .password(passwordEncoder().encode("123456789"))
                    .build();

            //TODO: Agregarle la logica de asignacion de administrador (No correrá sin eso).

            //userRepository.save(admin);
        };
    }
}
