package org.forumflow.backend.user.application.service;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.infraestructure.model.request.ChangePasswordRequest;
import org.forumflow.backend.user.infraestructure.model.request.UpdateInfoRequest;
import org.forumflow.backend.user.infraestructure.model.response.UserDetailResponse;

public interface IUserService {
    void changePassword(ChangePasswordRequest request, User user);
    UserDetailResponse updateUserInfo(UpdateInfoRequest request, User user);
}
