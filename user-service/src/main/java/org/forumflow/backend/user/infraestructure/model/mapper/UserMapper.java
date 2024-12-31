package org.forumflow.backend.user.infraestructure.model.mapper;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.domain.entity.UserDetail;
import org.forumflow.backend.user.infraestructure.model.projection.UserProjection;
import org.forumflow.backend.user.infraestructure.model.response.UserDetailResponse;
import org.forumflow.backend.user.infraestructure.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.userDetail", target = "userDetail", qualifiedByName = "mapUserDetail")
    UserResponse toUserResponse(User user);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "userDetail.firstname", target = "userDetail.firstname")
    @Mapping(source = "userDetail.lastname", target = "userDetail.lastname")
    @Mapping(source = "userDetail.email", target = "userDetail.email")
    UserResponse toUserResponse(UserProjection userProjection);

    @Mapping(source = ".", target = ".")
    UserDetailResponse toUserDetailResponse(UserDetail userDetail);

    @Named("mapUserDetail")
    default UserDetailResponse mapUserDetail(UserDetail userDetail) {
        if (userDetail == null) {
            return null;
        }
        return new UserDetailResponse(
                userDetail.getFirstname(),
                userDetail.getLastname(),
                userDetail.getEmail()
        );
    }
}
