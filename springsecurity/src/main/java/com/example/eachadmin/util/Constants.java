package com.example.eachadmin.util;

import com.example.eachadmin.constants.SecurityConstants;
import com.example.eachadmin.response.ResponseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.csrf.CsrfToken;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static io.jsonwebtoken.Jwts.builder;

public class Constants {
    // Session 中存储图形验证码的属性名
    public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    public static void loginSuccessShare(HttpServletRequest request, HttpServletResponse response, Authentication authentication, RedisTemplate<String, Object> redisTemplate) throws IOException {
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        String jwt = builder().issuer("pengtao").subject("JWT Token")
                .claim("username", authentication.getCredentials())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + 30000000))
                .signWith(key).compact();
        response.setHeader(SecurityConstants.JWT_HEADER, jwt);
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken.getHeaderName() != null) {
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }
        // 这里可以登录成功后将用户可以访问的资源添加到redis集合里面(基于rbac将用户对应角色可以访问的权限资源)
        redisTemplate.opsForSet().add("mySet", "/test02");
        // 设置响应的内容类型和字符编码
        response.setContentType("text/html;charset=UTF-8");
        ResponseResult<Authentication> success = ResponseResult.success(authentication);
        // 将响应数据序列化为 JSON 字符串
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(success);
        // 获取输出流并写入响应数据
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
    public static void loginFailureShare(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
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
        private Set<String> populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return authoritiesSet;
    }
}
