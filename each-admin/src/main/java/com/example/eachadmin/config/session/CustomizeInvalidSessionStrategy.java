package com.example.eachadmin.config.session;
/*
自定义定制失效会话的策略
 */

import com.example.eachadmin.config.context.CustomizeSecurityContextRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class CustomizeInvalidSessionStrategy implements InvalidSessionStrategy {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 设置响应的内容类型和字符编码
        response.setContentType("text/html;charset=UTF-8");
        // 输出会话已过期的提示信息到浏览器
        String id = request.getSession().getId();
        // 删除对应用户认证的信息
        redisTemplate.delete(CustomizeSecurityContextRepository.SECURITY_CONTEXT_KEY+id);
        // 删除对应的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    cookie.setMaxAge(0); // 设置cookie的过期时间为0，浏览器会将其删除
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        response.getWriter().write("会话已过期，请重新登录");
    }
}
