package com.example.eachadmin.config.csrf;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import static com.example.eachadmin.constants.SecurityConstants.EXCLUDED_PATHS;

/**
 * 自定义CSRF配置的方法。
 * 用于配置CSRF保护的构建器。
 */
public class CustomizeCsrfConfig implements Customizer<CsrfConfigurer<HttpSecurity>> {
    @Override
    public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
        // 忽略对"/login"路径的CSRF保护，即不对这些请求进行CSRF令牌的验证。
        httpSecurityCsrfConfigurer
                .ignoringRequestMatchers("/image","/login")
                // 设置自定义的CSRF令牌请求处理器。
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                // 设置自定义的CSRF令牌存储库,这个存储库负责存储和管理CSRF令牌。
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                // 设置会话认证策略。
                //.sessionAuthenticationStrategy();
    }
}
