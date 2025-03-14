package com.example.eachadmin.config.authentication;

import com.example.eachadmin.config.authorization.RbacAuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;


@Component
public class AuthReqConfig implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Autowired
    private RbacAuthManager rbacAuthManager;

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
        authorizationManagerRequestMatcherRegistry
                .requestMatchers("/login", "/image","/oauth","/login/oauth")
                .permitAll()
                .anyRequest()
                .access(rbacAuthManager);
        }
}