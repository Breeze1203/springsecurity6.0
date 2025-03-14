package com.example.eachadmin.config.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizationFailureHandler;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;

import java.util.Map;

/**
 * @ClassName oAuthFailure
 * @Author pt
 * @Description 自定义Oauth认证失败处理
 * @Date 2024/10/31 16:37
 **/
public class oAuthFailure implements OAuth2AuthorizationFailureHandler {
    private static oAuthFailure instance;
    @Override
    public void onAuthorizationFailure(OAuth2AuthorizationException authorizationException, Authentication principal, Map<String, Object> attributes) {
        System.out.println("授权失败"+authorizationException.getMessage());
    }

    private oAuthFailure(){};

    public static oAuthFailure getInstance() {
        if (instance == null) {
            instance = new oAuthFailure();
        }
        return instance;
    }

}
