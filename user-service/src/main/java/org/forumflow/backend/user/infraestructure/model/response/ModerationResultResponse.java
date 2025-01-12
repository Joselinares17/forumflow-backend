package org.forumflow.backend.user.infraestructure.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.Duration;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ModerationResultResponse(
        boolean success,
        String action,
        Long userId,
        String message,
        Duration duration
) {
}
