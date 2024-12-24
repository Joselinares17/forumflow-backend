package org.forumflow.backend.user.application.service;

import org.forumflow.backend.user.infraestructure.model.request.ChangePasswordRequest;

import java.security.Principal;

public interface IUserService {
    void changePassword(ChangePasswordRequest request, Principal principal);
}
