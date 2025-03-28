package com.example.eachadmin.config.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.stereotype.Component;

@Component
public class SessionConfig implements Customizer<SessionManagementConfigurer<HttpSecurity>> {


    @Autowired
    private InvalidSessionStrategy sessionStrategy;

    @Override
    public void customize(SessionManagementConfigurer<HttpSecurity> httpSecuritySessionManagementConfigurer) {
        httpSecuritySessionManagementConfigurer
                .invalidSessionStrategy(sessionStrategy)//会话过期
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(new SessionRegistryImpl())
                .expiredSessionStrategy(new SessionExpiredStrategy());
    }
}
