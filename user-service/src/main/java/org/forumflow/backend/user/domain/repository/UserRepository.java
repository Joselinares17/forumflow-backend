package org.forumflow.backend.user.domain.repository;

import org.forumflow.backend.user.domain.entity.User;
import org.forumflow.backend.user.infraestructure.model.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userDetail.email = :email")
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u.username as username, u.userDetail as userDetail FROM User u JOIN u.userDetail d")
    Page<UserProjection> findAllUsersWithDetails(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.accountNonLocked = false AND u.suspensionStart IS NOT NULL AND u.suspensionDuration IS NOT NULL")
    List<User> findAllSuspendedUsers();

    @Query("SELECT u FROM User u WHERE u.enabled = false AND u.banStart IS NOT NULL AND u.banDuration IS NOT NULL")
    List<User> findAllBannedUsers();
}
