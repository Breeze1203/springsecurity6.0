package com.example.eachadmin.config.Authorization;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import java.util.function.Supplier;


public class CustomizeAuthorizationManager implements AuthorizationManager<HttpServletRequest> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest object) {
        boolean authenticated = authentication.get().isAuthenticated();
        return new AuthorizationDecision(false);
    }
}
