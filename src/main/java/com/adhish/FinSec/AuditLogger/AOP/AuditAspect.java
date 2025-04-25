package com.adhish.FinSec.AuditLogger.AOP;

import com.adhish.FinSec.AuditLogger.entity.AuditLog;
import com.adhish.FinSec.Entity.User;
import com.adhish.FinSec.AuditLogger.Repo.AuditLogRepository;
import com.adhish.FinSec.Repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  HttpServletRequest request;

    @Pointcut("execution(* com.adhish.FinSec.controller.*.*(..))")
    public void controllerMethods() {};

    @AfterReturning(value = "controllerMethods()",returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result){
        saveLog(joinPoint,"SUCCESS");
    }

    @AfterThrowing(value = "controllerMethods()",throwing = "ex")
    public void logFailure(JoinPoint  joinPoint,Exception ex){
        saveLog(joinPoint, "FAILURE");
    }

    private void saveLog(JoinPoint joinPoint,String status){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null) return;

        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setActionType(joinPoint.getSignature().getName());
        log.setEndpoint(request.getRequestURI());
        log.setStatus(status);
        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log);


    }

}
