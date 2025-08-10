package com.shop.smart_commerce_api.configurations.security;

import com.shop.smart_commerce_api.exception.AppException;
import com.shop.smart_commerce_api.exception.ErrorCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    private final CustomPermissionEvaluator permissionEvaluator;

    public PermissionAspect(CustomPermissionEvaluator permissionEvaluator) {
        this.permissionEvaluator = permissionEvaluator;
    }

    @Before("@annotation(hasPermission)")
    public void checkPermission(JoinPoint joinPoint, HasPermission hasPermission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String requiredPermission = hasPermission.value();
        if (!permissionEvaluator.hasPermission(authentication, requiredPermission)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
    }
}
