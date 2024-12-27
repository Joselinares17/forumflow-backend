package org.forumflow.backend.user.infraestructure.model.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        String code,
        Map<String, String> body,
        LocalDateTime localDateTime
) {
}
