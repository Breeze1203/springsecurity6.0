package com.example.eachadmin.exception;

import com.example.eachadmin.config.authentication.AuthFailure;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import java.io.IOException;

public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {
    private static CustomizeAccessDeniedHandler instance;

    public static CustomizeAccessDeniedHandler getInstance() {
        if (instance == null) {
            instance = new CustomizeAccessDeniedHandler();
        }
        return instance;
    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (accessDeniedException instanceof InvalidCsrfTokenException){
            AuthFailure.getInstance().onAuthenticationFailure(request,response,new BadCredentialsException("请输入正确的csrf令牌"));
            return;
        } else if (accessDeniedException instanceof MissingCsrfTokenException) {
            AuthFailure.getInstance().onAuthenticationFailure(request,response,new BadCredentialsException("尚未登录，请登录"));
            return;
        }
        AuthFailure.getInstance().onAuthenticationFailure(request,response,new BadCredentialsException("你暂无权限访问"));
    }
}
