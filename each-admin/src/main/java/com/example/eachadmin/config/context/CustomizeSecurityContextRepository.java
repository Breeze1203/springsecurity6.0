package com.example.eachadmin.config.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.DeferredSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class CustomizeSecurityContextRepository implements SecurityContextRepository {

    public static final String SECURITY_CONTEXT_KEY = "spring:security:context";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        // 获取sessionId
        String sessionId = requestResponseHolder.getRequest().getSession().getId();
        // 看redis里面有没有会话信息
        String context = (String) redisTemplate.opsForValue().get(SECURITY_CONTEXT_KEY + ":" + sessionId);
        if (context == null) {
            // 没有会话信息
            return SecurityContextHolder.createEmptyContext();
        }else {
            // 从字符串反序列化为 SecurityContext 对象
            SecurityContext con = new SecurityContextImpl();
            Authentication authentication = new UsernamePasswordAuthenticationToken(context, null, null);
            con.setAuthentication(authentication);
            return con;
        }
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        String sessionId = request.getSession().getId();
        redisTemplate.opsForValue().set(SECURITY_CONTEXT_KEY + ":" + sessionId, context.getAuthentication().getPrincipal().toString(),30, TimeUnit.MINUTES);
        SecurityContextHolder.setContext(context);
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return Boolean.TRUE.equals(redisTemplate.hasKey(SECURITY_CONTEXT_KEY + ":" + sessionId));
    }

    @Override
    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
        return SecurityContextRepository.super.loadDeferredContext(request);
    }
}
