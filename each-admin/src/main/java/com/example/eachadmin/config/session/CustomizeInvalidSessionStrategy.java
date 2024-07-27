package com.example.eachadmin.config.session;
/*
自定义定制失效会话的策略
 */

import com.example.eachadmin.config.Authentication.CustomizeAuthenticationFailureHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class CustomizeInvalidSessionStrategy implements InvalidSessionStrategy {

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 设置响应的内容类型和字符编码
        response.setContentType("text/html;charset=UTF-8");
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
        CustomizeAuthenticationFailureHandler.getInstance().onAuthenticationFailure(request,response,new BadCredentialsException("会话失效，请重新登录"));
    }
}
