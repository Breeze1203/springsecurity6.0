package com.example.eachadmin.config.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CustomizeContextConfig implements Customizer<SecurityContextConfigurer<HttpSecurity>> {
    @Autowired
    private CustomizeSecurityContextRepository contextRepository;

    @Override
    public void customize(SecurityContextConfigurer<HttpSecurity> httpSecuritySecurityContextConfigurer) {
        httpSecuritySecurityContextConfigurer.securityContextRepository(contextRepository);
    }
}
