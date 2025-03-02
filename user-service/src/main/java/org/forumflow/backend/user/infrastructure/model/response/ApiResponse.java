package org.forumflow.backend.user.infrastructure.model.response;

import java.util.Map;

public record ApiResponse<T>(
        String message,
        int status,
        T body,
        Map<String, Object> metadata
) {
}
