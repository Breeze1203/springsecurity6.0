package com.example.eachadmin.config.Authentication;

import com.example.eachadmin.response.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;


public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static CustomizeAuthenticationFailureHandler instance;

    public static CustomizeAuthenticationFailureHandler getInstance() {
        if (instance == null) {
            instance = new CustomizeAuthenticationFailureHandler();
        }
        return instance;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 设置响应的内容类型和字符编码
        response.setContentType("text/html;charset=UTF-8");
        ResponseResult<String> fail = ResponseResult.fail(exception.getMessage());
        // 将响应数据序列化为 JSON 字符串
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(fail);
        // 获取输出流并写入响应数据
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

}
