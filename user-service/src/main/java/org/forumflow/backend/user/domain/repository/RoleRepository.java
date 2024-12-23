package org.forumflow.backend.user.domain.repository;

import org.forumflow.backend.user.domain.entity.Role;
import org.forumflow.backend.user.domain.entity.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByTypeRole(TypeRole typeRole);
}
