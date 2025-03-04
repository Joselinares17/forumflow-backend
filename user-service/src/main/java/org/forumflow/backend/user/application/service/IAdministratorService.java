package org.forumflow.backend.user.application.service;

import org.forumflow.backend.user.infrastructure.helper.SearchCriteria;
import org.forumflow.backend.user.infrastructure.model.response.AdminActionResponse;
import org.forumflow.backend.user.infrastructure.model.response.AuditResponse;
import org.forumflow.backend.user.infrastructure.model.response.ModerationResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAdministratorService {
    ModerationResultResponse suspendAccountPermanently(Long id);

    ModerationResultResponse unsuspendAccount(Long id);

    ModerationResultResponse banAccountPermanently(Long id);

    ModerationResultResponse unbanAccount(Long id);

    AdminActionResponse viewAuditLogs(SearchCriteria criteria);

    AdminActionResponse deleteUserAccount(Long userId);

    Page<AuditResponse> getUserActivityLogs(Long userId);

    Page<AuditResponse> getUserActivityByUsername(String username, Pageable pageable);
}
