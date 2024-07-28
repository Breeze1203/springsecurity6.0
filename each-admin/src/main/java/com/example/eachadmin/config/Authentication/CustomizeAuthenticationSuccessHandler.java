package com.example.eachadmin.config.Authentication;

import com.example.eachadmin.constants.SecurityConstants;
import com.example.eachadmin.response.ResponseResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public final class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder().issuer("pengtao").subject("JWT Token")
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
        ResponseResult<String> success = ResponseResult.success();
        // 将响应数据序列化为 JSON 字符串
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(success);
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
