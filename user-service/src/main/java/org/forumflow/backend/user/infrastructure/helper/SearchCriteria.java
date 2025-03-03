package org.forumflow.backend.user.infrastructure.helper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class SearchCriteria {
    private String actionType;
    private Long entityId;
    private Long performedBy;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> actionTypes;

    public long getDiferenceTimeInHours() {
        return Duration.between(this.startDate, this.endDate).toHours();
    }
}
