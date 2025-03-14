package com.example.eachadmin.config.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

import java.util.Map;

/**
 * @ClassName oAuth2Success
 * @Author pt
 * @Description 自定义Oauth认证成功处理
 * @Date 2024/10/31 16:23
 **/
public class oAuth2Success implements OAuth2AuthorizationSuccessHandler {

    @Override
    public void onAuthorizationSuccess(OAuth2AuthorizedClient authorizedClient, Authentication principal, Map<String, Object> attributes) {
        System.out.println("授权成功");
    }

    private static oAuth2Success instance;

    private oAuth2Success() {
    }

    public static oAuth2Success getInstance() {
        if (instance == null) {
            instance = new oAuth2Success();
        }
        return instance;
    }
}
