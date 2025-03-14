package com.example.eachadmin.exception;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;

public class CustomizeExceptionHandler implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {
    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer) {
        httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(CustomizeAccessDeniedHandler.getInstance());
    }

    private static CustomizeExceptionHandler instance;

    public static CustomizeExceptionHandler getInstance() {
        if (instance == null) {
            instance = new CustomizeExceptionHandler();
        }
        return instance;
    }
}
