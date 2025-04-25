package com.adhish.FinSec.AuditLogger.controller;

import com.adhish.FinSec.AuditLogger.entity.AuditLog;
import com.adhish.FinSec.AuditLogger.Repo.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/audit-log")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @PreAuthorize("hasRole('MANAGER', 'TELLER')")
    @GetMapping("/all")
    public List<AuditLog> getAllLogs(){
        return auditLogRepository.findAll();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('MANAGER', 'TELLER')")
    public List<AuditLog> getLogsByUser(@PathVariable Long id){
        return auditLogRepository.findAll()
                .stream()
                .filter(log -> log.getUser() != null && log.getUser().getId().equals(id))
                .toList();
    }
}
