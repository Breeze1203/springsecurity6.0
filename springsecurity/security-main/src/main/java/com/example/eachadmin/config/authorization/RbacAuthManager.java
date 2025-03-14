package com.example.eachadmin.config.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import java.util.Set;
import java.util.function.Supplier;

@Component
public class RbacAuthManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private PerService perService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Authentication auth = authentication.get();
        if (auth == null || !auth.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        String requestUrl = context.getRequest().getRequestURI();
        Set<String> allowedUrls = perService.getAllowedUrlsForUser(auth.getName());
        // 模拟投票逻辑
        boolean pathMatched = allowedUrls.stream().anyMatch(pattern -> new AntPathMatcher().match(pattern, requestUrl));
        boolean hasAdminRole = auth.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        // 决策：路径匹配或拥有管理员角色则放行
        boolean isAllowed = pathMatched || hasAdminRole;
        return new AuthorizationDecision(isAllowed);
    }
}
