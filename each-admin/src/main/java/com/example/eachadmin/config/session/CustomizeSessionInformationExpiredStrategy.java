package com.example.eachadmin.config.session;



import com.example.eachadmin.config.context.CustomizeSecurityContextRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

/*
会话失效
 */
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {


    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        // 1. 获取用户名
        Object principal = event.getSessionInformation().getPrincipal();
        HttpServletResponse response = event.getResponse();
        // 设置响应的内容类型和字符编码
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(principal.toString()+"用户已在其它电脑登录");
    }
}
