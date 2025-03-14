package com.example.eachadmin.config.logout;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class LogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 设置响应的内容类型和字符编码
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(authentication.getPrincipal().toString()+"退出登录成功");
        /*
        这里进行一些操作，包括删除认证信息
         */
    }
}
