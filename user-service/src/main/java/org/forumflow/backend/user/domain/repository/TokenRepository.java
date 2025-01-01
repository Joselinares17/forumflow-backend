package org.forumflow.backend.user.domain.repository;

import org.forumflow.backend.user.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t INNER JOIN User u on t.user.id = u.id WHERE u.id = :id " +
            "AND (t.expired = false OR t.revoked = false)")
    List<Token> findAllValidTokenByUser(Long id);
    Optional<Token> findByToken(String token);

    @Modifying
    @Query("DELETE FROM Token t WHERE t.id = :id")
    void deleteAllByUserId(Long id);
}
