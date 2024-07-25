package com.example.eachadmin.config.form;

import com.example.eachadmin.config.Authentication.CustomizeAuthenticationFailureHandler;
import com.example.eachadmin.config.Authentication.CustomizeAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.stereotype.Component;
/*
自定义表单配置
 */

@Component
public class CustomizeFormConfig implements Customizer<FormLoginConfigurer<HttpSecurity>>{

    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
        httpSecurityFormLoginConfigurer
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new CustomizeAuthenticationSuccessHandler())
                .failureHandler(new CustomizeAuthenticationFailureHandler())
                .permitAll();
    }
}
