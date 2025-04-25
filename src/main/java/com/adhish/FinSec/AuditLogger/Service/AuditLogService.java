package com.adhish.FinSec.AuditLogger.Service;

import com.adhish.FinSec.AuditLogger.Repo.AuditLogRepository;
import com.adhish.FinSec.AuditLogger.entity.AuditLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    // Example service methods
    public void logAction(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }
}
