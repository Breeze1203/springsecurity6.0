package com.example.eachadmin.filter;

import com.example.eachadmin.config.Authentication.CustomizeAuthenticationFailureHandler;
import com.example.eachadmin.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.util.StringUtils;
import static com.example.eachadmin.constants.SecurityConstants.EXCLUDED_PATHS;


public class JwtTokenValidatorFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!EXCLUDED_PATHS.contains(request.getRequestURI())) {
            String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
            if (!StringUtils.hasText(jwt)) {
                CustomizeAuthenticationFailureHandler.getInstance().onAuthenticationFailure(request, response, new BadCredentialsException("令牌不存在"));
                return;
            }
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
            } catch (Exception e) {
                // 清除安全上下文的认证信息并抛出异常
                SecurityContextHolder.clearContext();
                CustomizeAuthenticationFailureHandler.getInstance().onAuthenticationFailure(request, response, new BadCredentialsException(e.getMessage()));
                return;
            }
        }
        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}