package com.example.eachadmin.config;


import com.example.eachadmin.config.Authentication.CustomizeAuthorizeHttpRequestsConfig;
import com.example.eachadmin.config.Authentication.provider.SmsAuthenticationProvider;
import com.example.eachadmin.config.Authentication.provider.UsernamePasswordAuthenticationProvider;
import com.example.eachadmin.config.context.CustomizeContextConfig;
import com.example.eachadmin.config.cors.CustomizeCorsConfig;
import com.example.eachadmin.config.csrf.CustomizeCsrfConfig;
import com.example.eachadmin.config.form.CustomizeFormConfig;
import com.example.eachadmin.config.logout.CustomizeLogoutConfig;
import com.example.eachadmin.config.remember.CustomizeRememberConfig;
import com.example.eachadmin.config.session.CustomizeSessionConfig;
import com.example.eachadmin.filter.*;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.util.Properties;


@Configuration
public class SecurityConfig {

    @Autowired
    private CustomizeContextConfig contextConfig;

    @Autowired
    private CustomizeSessionConfig sessionConfig;

    @Autowired
    private CustomizeRememberConfig rememberConfig;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    @Autowired
    private CustomizeFormConfig formConfig;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(new CustomizeAuthorizeHttpRequestsConfig());
        http.authenticationProvider(daoAuthenticationProvider());
        // 配置表单
        http.formLogin(formConfig);
        http.csrf(new CustomizeCsrfConfig());
        // 配置用户登录认证后的信息存储位置
        http.securityContext(contextConfig);
        // 配置session
        http.sessionManagement(sessionConfig);// 会话失效
        // 记住密码相关配置
        http.rememberMe(rememberConfig);
        // 跨域配置
        http.cors(new CustomizeCorsConfig());
        // 配置登出
        http.logout(new CustomizeLogoutConfig());
        http.addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new JwtTokenValidatorFilter(),BasicAuthenticationFilter.class);
        http.addFilterAt(new AuthoritiesLoggingAtFilter(),BasicAuthenticationFilter.class);
        http.addFilterAfter(new AuthoritiesLoggingAfterFilter(),BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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


    // 定义DaoAuthenticationProvider Bean
    @Bean
    public UsernamePasswordAuthenticationProvider daoAuthenticationProvider() {
        return new UsernamePasswordAuthenticationProvider();
    }

    // SmsAuthenticationProvider Bean
    @Bean
    public SmsAuthenticationProvider smsAuthenticationProvider() {
        return new SmsAuthenticationProvider();
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
