package com.example.eachadmin.config.form;

import com.example.eachadmin.config.authentication.AuthFailure;
import com.example.eachadmin.config.authentication.AuthSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.stereotype.Component;
/*
自定义表单配置
 */

@Component
public class FromConfig implements Customizer<FormLoginConfigurer<HttpSecurity>>{

    @Autowired
    private AuthSuccess successHandler;
    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
        httpSecurityFormLoginConfigurer
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureHandler(new AuthFailure())
                .permitAll();
    }
}
