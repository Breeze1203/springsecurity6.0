package com.example.eachadmin.config.oauth.login;

import com.example.eachadmin.config.authentication.CustomizeAuthenticationFailureHandler;
import com.example.eachadmin.config.authentication.CustomizeAuthenticationSuccessHandler;
import com.example.eachadmin.config.oauth.client.CustomizeClientRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.*;
import org.springframework.stereotype.Component;


@Component
public class OAuth2LoginConfig implements Customizer<OAuth2LoginConfigurer<HttpSecurity>> {

    @Autowired
    private OAuth2AuthenticationSuccessHandler successHandler;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void customize(OAuth2LoginConfigurer<HttpSecurity> loginConfigurer) {
        loginConfigurer
                .authorizationEndpoint(authorizationEndpointConfig ->
                        // 自定义授权请求
                        authorizationEndpointConfig.authorizationRequestResolver(authorizationRequestResolver(googleClientRegistration())))
                // 存储 OAuth2 授权信息的服务
                .authorizedClientService(new JdbcOAuth2AuthorizedClientService(jdbcTemplate,googleClientRegistration()))
                // 获取特定客户端的注册信息
                .clientRegistrationRepository(googleClientRegistration())
                // HttpSessionOAuth2AuthorizedClientRepository 配置为 Spring Security 中用于管理已授权的 OAuth2 客户端信息的存储库。
                // 这样可以确保在用户登录并授权后，客户端的访问令牌等信息可以在用户会话期间被正确地管理和使用
                .authorizedClientRepository(new HttpSessionOAuth2AuthorizedClientRepository())
                // 配置oauth认证成功
                .successHandler(successHandler)
                // 配置认证失败
                .failureHandler(new CustomizeAuthenticationFailureHandler());
    }

    private OAuth2AuthorizationRequestResolver authorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository, "/oauth2/authorization");
    }

    @Bean
    private ClientRegistrationRepository googleClientRegistration() {
        return new CustomizeClientRegistrationRepository();
    }

}
