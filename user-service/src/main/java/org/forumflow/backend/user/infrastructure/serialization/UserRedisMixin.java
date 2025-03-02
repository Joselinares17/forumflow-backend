package org.forumflow.backend.user.infrastructure.serialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.forumflow.backend.user.domain.entity.Token;
import org.forumflow.backend.user.domain.entity.UserDetail;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface UserRedisMixin {
    @JsonIgnore
    UserDetail getUserDetail();

    @JsonIgnore
    List<Token> getTokens();
}
