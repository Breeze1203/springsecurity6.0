package com.example.eachadmin.config.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CustomizeSessionConfig implements Customizer<SessionManagementConfigurer<HttpSecurity>> {


    @Autowired
    private CustomizeInvalidSessionStrategy sessionStrategy;

    @Override
    public void customize(SessionManagementConfigurer<HttpSecurity> httpSecuritySessionManagementConfigurer) {
        httpSecuritySessionManagementConfigurer
                .invalidSessionStrategy(sessionStrategy)//会话过期
                .maximumSessions(1)
                //.maxSessionsPreventsLogin(true)
                .sessionRegistry(new CustomizeSessionRegistryImpl())
                .expiredSessionStrategy(new CustomizeSessionInformationExpiredStrategy());
    }
}
