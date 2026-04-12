package com.example.userservice.aop;

import com.example.userservice.exception.AuthenticationException;
import com.example.userservice.security.jwt.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LoginCheckAspect {

    @Autowired
    private JwtOperator jwtOperator;

    /**
     * 环绕通知：拦截所有标注了 @RequireLogin 的方法或类
     * 支持两种使用方式：
     * 1. 方法级别：在具体方法上添加 @RequireLogin
     * 2. 类级别：在 Controller 类上添加 @RequireLogin，该类所有方法都需要登录
     */
    @Around("@annotation(com.example.userservice.aop.annotation.RequireLogin) || " +
            "@within(com.example.userservice.aop.annotation.RequireLogin)")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new AuthenticationException(500,"请先登录");
        }

        HttpServletRequest request = attributes.getRequest();
        String token = extractToken(request);
        if (!StringUtils.hasText(token)) {
            throw new AuthenticationException(500,"请先登录");
        }

        try {
            Claims claims = jwtOperator.getClaimsFromToken(token);
            if (!jwtOperator.validateToken(token)) {
                throw new AuthenticationException("Token无效或已过期");
            }

            request.setAttribute("id", claims.get("id"));
            request.setAttribute("wxNickName", claims.get("wxNickName"));
            request.setAttribute("role", claims.get("role"));
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            log.warn("Token校验失败: {}", e.getMessage());
            throw new AuthenticationException("Token无效或已过期");
        }

        log.info("用户:{}已登录", request.getAttribute("wxNickName"));

        return joinPoint.proceed();
    }

    private String extractToken(HttpServletRequest request) {
//        String authorization = request.getHeader("Authorization");
//        if (StringUtils.hasText(authorization)) {
//            if (authorization.startsWith("Bearer ")) {
//                return authorization.substring(7).trim();
//            }
//            return authorization.trim();
//        }
        String token = request.getHeader("X-Token");
        return StringUtils.hasText(token) ? token.trim() : null;
    }
}
