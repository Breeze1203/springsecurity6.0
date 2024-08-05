package com.example.eachadmin.config.authentication;

import com.example.eachadmin.config.authorization.RBACAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;


@Component
public class CustomizeAuthorizeHttpRequestsConfig implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Autowired
    private RBACAuthorizationManager rbacAuthorizationManager;

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
        authorizationManagerRequestMatcherRegistry
                .requestMatchers("/login", "/image","/login/oauth")
                .permitAll()
                .anyRequest()
                .access(rbacAuthorizationManager);
        }
}