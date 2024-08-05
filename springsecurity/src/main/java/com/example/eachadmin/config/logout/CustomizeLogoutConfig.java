package com.example.eachadmin.config.logout;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CustomizeLogoutConfig  implements Customizer<LogoutConfigurer<HttpSecurity>> {
    /**
     * 自定义配置 HTTP 安全性中的注销（logout）行为。
     * <p>
     * 该方法用于配置注销过程的各个方面，包括注销请求的 URL、注销成功时的处理器、清除认证信息以及在注销时使 HTTP 会话无效。
     *
     * @param httpSecurityLogoutConfigurer HTTP 安全性中的注销配置器，允许定制注销行为相关的参数和处理。
     */
    @Override
    public void customize(LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer) {
        httpSecurityLogoutConfigurer
                .logoutUrl("/logout")                       // 设置处理注销请求的 URL
                .permitAll(true)                            // 允许所有用户访问注销 URL
                .logoutSuccessHandler(new CustomizeLogoutHandler()) // 设置注销成功时的处理器
                .clearAuthentication(true)                  // 在注销时清除认证信息
                .invalidateHttpSession(true);               // 注销时使 HTTP 会话无效
    }

}
