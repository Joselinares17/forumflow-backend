package org.forumflow.backend.user.infraestructure.api;

import jakarta.servlet.http.HttpServletRequest;
import org.forumflow.backend.user.application.business.AuthenticationBusiness;
import org.forumflow.backend.user.infraestructure.model.request.AuthenticationRequest;
import org.forumflow.backend.user.infraestructure.model.request.RegisterRequest;
import org.forumflow.backend.user.infraestructure.model.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationBusiness authenticationBusiness;

    public AuthenticationController(AuthenticationBusiness authenticationBusiness) {
        this.authenticationBusiness = authenticationBusiness;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Validated @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationBusiness.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Validated @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationBusiness.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(authenticationBusiness.refreshToken(request));
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }
}
