package com.example.eachadmin.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class oAuth2LoginConfig {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .clientCredentials()
                        .password()
                        .build();
        /*
        这里面已经配置了授权成功或失败
         */
        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        /*
         授权成功
        authorizedClientManager.setAuthorizationSuccessHandler(oAuth2Success.getInstance());
         授权失败
        authorizedClientManager.setAuthorizationFailureHandler(oAuthFailure.getInstance());
         */
        return authorizedClientManager;
    }

    @Bean
    ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration github = githubClientRegistration();
        ClientRegistration facebook = facebookClientRegistration();
        ClientRegistration b = BClientRegistration();
        /*
        这里也可以传入一个集合，可以扩展从数据库获取
        ArrayList<ClientRegistration> clientRegistrations = new ArrayList<>();
        new InMemoryClientRegistrationRepository(clientRegistrations);
         */
        return new InMemoryClientRegistrationRepository(github, facebook,b);
    }

    private ClientRegistration githubClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("-----")
                .clientSecret("--------").build();
    }

    private ClientRegistration facebookClientRegistration() {
        return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook").clientId("-----")
                .clientSecret("----").build();
    }

    private ClientRegistration BClientRegistration(){
        ClientRegistration b = ClientRegistration.withRegistrationId("b")
                .clientId("b-client")
                .clientSecret("b-secret") // 明文，与 b-service 的加密密钥对应
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080/login/oauth2/code/b")
                .scope("read", "write")
                .authorizationUri("http://localhost:9090/oauth2/authorize")
                .tokenUri("http://localhost:9090/oauth2/token")
                .build();
        return b;
    }


    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate, clientRegistrationRepository);
    }

}
