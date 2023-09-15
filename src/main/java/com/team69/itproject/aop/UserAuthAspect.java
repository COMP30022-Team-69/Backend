package com.team69.itproject.aop;

import com.team69.itproject.aop.annotations.UserAuth;
import com.team69.itproject.exceptions.BusinessException;
import com.team69.itproject.utils.PublicUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class UserAuthAspect {
    @Autowired
    private PublicUtils publicUtils;

    @Pointcut("@annotation(com.team69.itproject.aop.annotations.UserAuth)")
    public void userAuth() {
    }

    @Around("userAuth()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(authorization.lastIndexOf("Bearer") + 7);
        Claims claims = publicUtils.getClaimsFromToken(token);
        String id = claims.get("id").toString();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if (!methodSignature.getMethod().isAnnotationPresent(PreAuthorize.class)) {
            return joinPoint.proceed();
        }
        UserAuth annotation = methodSignature.getMethod().getAnnotation(UserAuth.class);
        switch (annotation.value()) {
            case SELF -> {
                String userId = request.getHeader("User-Id");
                if (!userId.equals(id)) {
                    throw new BusinessException(401, "User ID not match");
                }
            }
            case PUBLIC -> {
                // do nothing
            }
        }

        log.debug("UserID : {}", id);
        return joinPoint.proceed();
    }
}
