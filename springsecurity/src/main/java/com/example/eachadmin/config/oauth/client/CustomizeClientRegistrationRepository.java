package com.example.eachadmin.config.oauth.client;

import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;


public class CustomizeClientRegistrationRepository implements ClientRegistrationRepository {
    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        if ("google".equals(registrationId)) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(registrationId)
                    .clientId("7******.com")
                    .clientSecret("G****88a")
                    .build();
        } else if ("github".equals(registrationId)) {
            return CommonOAuth2Provider.GITHUB.getBuilder(registrationId)
                    .clientId("O***88kN7")
                    .clientSecret("4***8*7bb52")
                    .build();
        } else {
            throw new IllegalArgumentException("Unknown registrationId: " + registrationId);
        }
    }


}
