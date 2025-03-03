package org.forumflow.backend.user.application.service;

import org.forumflow.backend.user.infrastructure.helper.SearchCriteria;
import org.forumflow.backend.user.infrastructure.model.response.AdminActionResponse;
import org.forumflow.backend.user.infrastructure.model.response.ModerationResultResponse;

public interface IAdministratorService {
    ModerationResultResponse suspendAccountPermanently(Long id);

    ModerationResultResponse unsuspendAccount(Long id);

    ModerationResultResponse banAccountPermanently(Long id);

    ModerationResultResponse unbanAccount(Long id);

    AdminActionResponse viewAuditLogs(SearchCriteria criteria);

    AdminActionResponse deleteUserAccount(Long userId);

    //Auditoria
//    Page<AuditLog> getAuditLogs(SearchCriteria criteria, Pageable pageable);
//    List<AuditLog> getUserActivityLogs(Long userId);
//    List<AuditLog> getModeratorActions(Long moderatorId);
}
