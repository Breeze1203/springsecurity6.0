package com.example.eachadmin.config.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ContextConfig implements Customizer<SecurityContextConfigurer<HttpSecurity>> {
    @Autowired
    private ContextRepository contextRepository;

    @Override
    public void customize(SecurityContextConfigurer<HttpSecurity> httpSecuritySecurityContextConfigurer) {
        httpSecuritySecurityContextConfigurer.securityContextRepository(contextRepository);
    }
}
