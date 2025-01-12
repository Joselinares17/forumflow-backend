package org.forumflow.backend.user.infraestructure.model.request;

import java.time.Duration;

public record SuspendRequest(
        String duration
) {
    public static boolean isInvalidDuration(String duration) {
        try {
            Duration.parse(duration);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
