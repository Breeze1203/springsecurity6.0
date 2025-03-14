package com.example.eachadmin.config.session;

import com.example.eachadmin.response.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;
import java.io.PrintWriter;

/*
会话失效
 */
public class SessionExpiredStrategy implements SessionInformationExpiredStrategy {


    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        // 1. 获取用户名
        Object principal = event.getSessionInformation().getPrincipal();
        HttpServletResponse response = event.getResponse();
        // 设置响应的内容类型和字符编码
        ResponseResult<String> fail = ResponseResult.fail(event.getSessionInformation().getPrincipal()+"用户已经登录");
        // 将响应数据序列化为 JSON 字符串
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(fail);
        // 获取输出流并写入响应数据
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
