package com.example.eachadmin.config.remember;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
/*
这个方法可以配置springsecurity自动登录之后的一些逻辑
大概思路是：从浏览器cookie获取到token令牌
但这个令牌是被springsecurity加密算法加密过，所以你得先解密
解密后获取到series 通过这个series查询用户，去令牌存储位置查找令牌以进行登录判断
具体逻辑可以参考TokenBasedRememberMeServices这个类里面的实现方法
 */




public class CustomizeRememberMeServices implements RememberMeServices {
    private final int tokenValiditySeconds = 1209600;

    private static final String REMEMBER_ME_COOKIE = "remember-me";


    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        // If no cookies are present, return null
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (REMEMBER_ME_COOKIE.equals(cookie.getName())) {
                String cookieValue = cookie.getValue();
                if (cookieValue != null) {
                    return null;
                }
            }
        }
        return null;
    }



    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        // 可以在这里实现登录失败的逻辑，例如记录登录失败次数等
        System.out.println("Login failed...");
    }

    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication
            successfulAuthentication) {
        // 可以在这里实现登录成功的逻辑，例如记录登录成功日志等
        System.out.println("Login succeeded for user: " + successfulAuthentication.getName());
    }

    /*
    这是翻源码找到的解密算法
     */
    public static  String[] decodeCookie(String cookieValue) throws InvalidCookieException {
        for(int j = 0; j < cookieValue.length() % 4; ++j) {
            cookieValue = cookieValue + "=";
        }
        String cookieAsPlainText;
        try {
            cookieAsPlainText = new String(Base64.getDecoder().decode(cookieValue.getBytes()));
        } catch (IllegalArgumentException var7) {
            throw new InvalidCookieException("Cookie token was not Base64 encoded; value was '" + cookieValue + "'");
        }

        String[] tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText, ":");

        for(int i = 0; i < tokens.length; ++i) {
            try {
                tokens[i] = URLDecoder.decode(tokens[i], StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException var6) {
                System.out.println("草泥马");
            }
        }

        return tokens;
    }
}