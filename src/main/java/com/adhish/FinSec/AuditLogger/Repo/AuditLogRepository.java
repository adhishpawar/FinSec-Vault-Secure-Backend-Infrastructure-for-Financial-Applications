package com.adhish.FinSec.AuditLogger.Repo;

import com.adhish.FinSec.AuditLogger.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
