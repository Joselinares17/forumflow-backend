package org.forumflow.backend.user.infraestructure.model.projection;

public interface UserProjection {
    String getUsername();
    UserDetailProjection getUserDetail();
}
