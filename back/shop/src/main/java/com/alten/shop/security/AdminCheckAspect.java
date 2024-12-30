package com.alten.shop.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminCheckAspect {

    @Around("@annotation(AdminOnly)")
    public Object checkAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && "admin@admin.com".equals(authentication.getName())) {
            return joinPoint.proceed();
        }
        
        throw new SecurityException("Admin access required");
    }
} 