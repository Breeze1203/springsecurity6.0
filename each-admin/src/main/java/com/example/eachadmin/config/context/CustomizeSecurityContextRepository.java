package com.example.eachadmin.config.context;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    public static final String SECURITY_CONTEXT_KEY = "context";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        // 获取sessionId，确保会话已经创建且响应尚未提交
        HttpSession session = requestResponseHolder.getRequest().getSession(false);
        if (session != null) {
            String sessionId = session.getId();
            // 看redis里面有没有会话信息
            String context = (String) redisTemplate.opsForValue().get(SECURITY_CONTEXT_KEY + ":" + sessionId);
            if (context != null) {
                // 从字符串反序列化为 SecurityContext 对象
                SecurityContext con = new SecurityContextImpl();
                Authentication authentication = new UsernamePasswordAuthenticationToken(context, null, null);
                con.setAuthentication(authentication);
                return con;
            }
        }
        // 没有会话信息或会话不存在
        return SecurityContextHolder.createEmptyContext();
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if(session.getId()!=null){
            String sessionId = session.getId();
            redisTemplate.opsForValue().set(SECURITY_CONTEXT_KEY + ":" + sessionId, context.getAuthentication().getPrincipal().toString(),30, TimeUnit.MINUTES);
            SecurityContextHolder.setContext(context);
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session!=null){
            return Boolean.TRUE.equals(redisTemplate.hasKey(SECURITY_CONTEXT_KEY + ":" + session.getId()));
        }
        return false;
    }

    @Override
    public DeferredSecurityContext loadDeferredContext(HttpServletRequest request) {
        return SecurityContextRepository.super.loadDeferredContext(request);
    }
}
