package org.forumflow.backend.user.infrastructure.model.projection;

public interface UserProjection {
    String getUsername();
    UserDetailProjection getUserDetail();
}
