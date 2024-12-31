package org.forumflow.backend.user.infraestructure.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String code,
        Map<String, String> body,
        LocalDateTime localDateTime
) {
}
