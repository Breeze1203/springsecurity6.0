package com.example.eachadmin.config.Authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.function.Supplier;

@Component
public class RBACAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Authentication auth = authentication.get();
        boolean authenticated = auth.isAuthenticated();
        Set<Object> urls = redisTemplate.opsForSet().members("mySet");
        if (!authenticated) return new AuthorizationDecision(false);
        if (urls != null) {
            return new AuthorizationDecision(urls.contains(object.getRequest().getRequestURI()));
        }
        return new AuthorizationDecision(false);
    }
}
