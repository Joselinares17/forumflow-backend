package org.forumflow.backend.user.domain.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.infraestructure.model.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("SELECT new org.forumflow.backend.user.infraestructure.model.dto.UserDto(u.id, u.username, u.userDetail.email)" +
//            "FROM User u WHERE u.userDetail.email = :email")
//    Optional<UserDto> findUserByEmail(@Param("email") String email);

    Optional<User> findByUsername(String username);
}
