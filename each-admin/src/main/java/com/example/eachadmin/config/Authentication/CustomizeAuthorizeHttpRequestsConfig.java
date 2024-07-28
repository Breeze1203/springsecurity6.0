package com.example.eachadmin.config.Authentication;

import com.example.eachadmin.config.Authorization.RBACAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;


@Component
public class CustomizeAuthorizeHttpRequestsConfig implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    @Autowired
    private RBACAuthorizationManager rbacAuthorizationManager;

    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {
        authorizationManagerRequestMatcherRegistry
                .requestMatchers("/login", "/image")
                .permitAll()
                .anyRequest()
                .access(rbacAuthorizationManager);
        }
}