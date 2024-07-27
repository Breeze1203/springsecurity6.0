package com.example.eachadmin.filter;

import com.example.eachadmin.config.Authentication.CustomizeAuthenticationFailureHandler;
import com.example.eachadmin.entity.CheckCode;
import com.example.eachadmin.exception.ValidateCodeException;
import com.example.eachadmin.util.Constants;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    private final String code;


    public ImageCodeValidateFilter() {
        this.code = "code";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {
        // 非 POST 方式的表单提交请求不校验图形验证码
        if ("/login".equals(request.getRequestURI()) && "POST".equals(request.getMethod())) {
            try {
                // 校验图形验证码合法性
                validate(request);
            } catch (ValidateCodeException e) {
                // 手动捕获图形验证码校验过程抛出的异常，将其传给失败处理器进行处理
                CustomizeAuthenticationFailureHandler.getInstance().onAuthenticationFailure(request, response, new BadCredentialsException(e.getMessage()));
                return;
            }
        }
        // 放行请求，进入下一个过滤器
        filterChain.doFilter(request, response);
    }

    // 判断验证码的合法性
    private void validate(HttpServletRequest request) {
        String requestCode = request.getParameter(code);
        if(requestCode == null) {
            requestCode = "";
        }
        requestCode = requestCode.trim();
        // 获取 Session
        HttpSession session = request.getSession();
        // 获取存储在 Session 里的验证码值
        CheckCode savedCode = (CheckCode) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (savedCode != null) {
            // 随手清除验证码，无论是失败，还是成功。客户端应在登录失败时刷新验证码
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
        }
        // 校验出错，抛出异常
        if (StringUtils.isBlank(requestCode)) {
            throw new ValidateCodeException("验证码的值不能为空");
        }
        if (savedCode == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (savedCode.isExpried()) {
            throw new ValidateCodeException("验证码过期");
        }
        if (!requestCode.equalsIgnoreCase(savedCode.getCode())) {
            throw new ValidateCodeException("验证码输入错误");
        }
    }
}