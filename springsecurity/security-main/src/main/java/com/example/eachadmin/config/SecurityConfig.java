package com.example.eachadmin.config;


import com.example.eachadmin.config.authentication.AuthManager;
import com.example.eachadmin.config.authentication.AuthSuccess;
import com.example.eachadmin.config.authentication.AuthReqConfig;
import com.example.eachadmin.config.authentication.provider.sms.SmsAuthFilter;
import com.example.eachadmin.config.context.ContextConfig;
import com.example.eachadmin.config.cors.CorsConfig;
import com.example.eachadmin.config.csrf.CsrfConfig;
import com.example.eachadmin.config.form.FromConfig;
import com.example.eachadmin.config.logout.LogoutConfig;
import com.example.eachadmin.config.remember.RememberConfig;
import com.example.eachadmin.config.session.SessionConfig;
import com.example.eachadmin.exception.CustomizeExceptionHandler;
import com.example.eachadmin.filter.*;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Properties;


@Configuration
public class SecurityConfig {

    private final AuthManager autoManager;

    private final ContextConfig contextConfig;

    private final SessionConfig sessionConfig;


    private final RememberConfig rememberConfig;


    private final ImageCodeValidateFilter imageCodeValidateFilter;


    private final FromConfig formConfig;


    private final AuthReqConfig requestsConfig;


    private final AuthSuccess successHandler;


    public SecurityConfig(AuthManager autoManager, ContextConfig contextConfig, SessionConfig sessionConfig, RememberConfig rememberConfig, ImageCodeValidateFilter imageCodeValidateFilter, FromConfig formConfig, AuthReqConfig requestsConfig, AuthSuccess successHandler) {
        this.autoManager = autoManager;
        this.contextConfig = contextConfig;
        this.sessionConfig = sessionConfig;
        this.rememberConfig = rememberConfig;
        this.imageCodeValidateFilter = imageCodeValidateFilter;
        this.formConfig = formConfig;
        this.requestsConfig = requestsConfig;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requestsConfig);
        http.authenticationManager(autoManager);
        // 配置表单
        http.formLogin(formConfig);
        http.csrf(new CsrfConfig());
        // 配置用户登录认证后的信息存储位置
        http.securityContext(contextConfig);
        // 配置session
        http.sessionManagement(sessionConfig);// 会话失效
        // 记住密码相关配置
        http.rememberMe(rememberConfig);
        // 跨域配置
        http.cors(new CorsConfig());
        http.exceptionHandling(CustomizeExceptionHandler.getInstance());
        // 配置登出
        http.logout(new LogoutConfig());
        http.addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new SmsAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JwtTokenValidatorFilter(),BasicAuthenticationFilter.class);
        http.addFilterAt(new AuthoritiesLoggingAtFilter(),BasicAuthenticationFilter.class);
        http.addFilterAfter(new AuthoritiesLoggingAfterFilter(),BasicAuthenticationFilter.class);
        http.oauth2Login().successHandler(successHandler);
        return http.build();
    }





    /*
    session事件监听
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices("pt_remember_security", userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.MD5);
        return rememberMe;
    }


    @Bean
    public DefaultKaptcha captchaProducer() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框
        properties.setProperty(Constants.KAPTCHA_BORDER, "yes");
        // 边框颜色
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "192,192,192");
        // 验证码图片的宽和高
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "110");
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "40");
        // 验证码颜色
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "0,0,0");
        // 验证码字体大小
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "32");
        // 验证码生成几个字符
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "5");
        // 验证码随机字符库
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ");
        // 验证码图片默认是有线条干扰的，我们设置成没有干扰
        properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
