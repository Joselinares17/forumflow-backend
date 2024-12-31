package org.forumflow.backend.user.application.service;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.infraestructure.model.request.ChangePasswordRequest;
import org.forumflow.backend.user.infraestructure.model.request.UpdateInfoRequest;
import org.forumflow.backend.user.infraestructure.model.response.UserDetailResponse;
import org.forumflow.backend.user.infraestructure.model.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    void changePassword(ChangePasswordRequest request, User user);
    UserDetailResponse updateUserInfo(UpdateInfoRequest request, User user);
    UserResponse getUserInfoByUsername(String username);
    Page<UserResponse> getAllUsersWithDetails(Pageable pageable);
}
