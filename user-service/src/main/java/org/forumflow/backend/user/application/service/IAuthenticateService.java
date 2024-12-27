package org.forumflow.backend.user.application.service;

import jakarta.servlet.http.HttpServletRequest;
import org.forumflow.backend.user.infraestructure.model.request.AuthenticationRequest;
import org.forumflow.backend.user.infraestructure.model.request.RegisterRequest;
import org.forumflow.backend.user.infraestructure.model.response.AuthenticationResponse;

public interface IAuthenticateService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    AuthenticationResponse refreshToken(HttpServletRequest request);
}
