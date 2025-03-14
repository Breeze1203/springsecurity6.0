package com.example.eachadmin.config.authentication;

import com.example.eachadmin.config.authentication.provider.SmsAuthProvider;
import com.example.eachadmin.config.authentication.provider.UserPwdAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthManager implements AuthenticationManager {

    private final List<AuthenticationProvider> providers;

    @Autowired
    public AuthManager(UserPwdAuthProvider usernameProvider,
                       SmsAuthProvider smsProvider) {
        this.providers = Arrays.asList(usernameProvider, smsProvider);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null) {
            throw new AuthenticationException("Authentication cannot be null") {};
        }
        // 遍历所有 AuthenticationProvider
        for (AuthenticationProvider provider : providers) {
            if (provider.supports(authentication.getClass())) {
                Authentication result = provider.authenticate(authentication);
                if (result != null) {
                    return result; // 认证成功，返回结果
                }
            }
        }

        // 没有找到支持的提供者或认证失败
        throw new AuthenticationException("No suitable AuthenticationProvider found for " + authentication.getClass().getName()) {};
    }
}