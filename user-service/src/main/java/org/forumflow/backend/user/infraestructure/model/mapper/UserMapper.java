package org.forumflow.backend.user.infraestructure.model.mapper;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.infraestructure.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(source = "user", target = "userDto")
//    @Mapping(source = "user.userDetail.email", target = "email")
//    UserResponse toUserResponse(User user);
}
