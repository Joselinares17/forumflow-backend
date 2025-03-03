package org.forumflow.backend.user.application.business;

import org.forumflow.backend.user.application.service.IAdministratorService;
import org.forumflow.backend.user.infrastructure.helper.SearchCriteria;
import org.forumflow.backend.user.infrastructure.model.response.AdminActionResponse;
import org.forumflow.backend.user.infrastructure.model.response.ModerationResultResponse;
import org.springframework.stereotype.Service;

@Service
public class AdministratorBusiness implements IAdministratorService {

    @Override
    public ModerationResultResponse suspendAccountPermanently(Long id) {
        return null;
    }

    @Override
    public ModerationResultResponse unsuspendAccount(Long id) {
        return null;
    }

    @Override
    public ModerationResultResponse banAccountPermanently(Long id) {
        return null;
    }

    @Override
    public ModerationResultResponse unbanAccount(Long id) {
        return null;
    }

    @Override
    public AdminActionResponse viewAuditLogs(SearchCriteria criteria) {
        return null;
    }

    @Override
    public AdminActionResponse deleteUserAccount(Long userId) {
        return null;
    }
}
