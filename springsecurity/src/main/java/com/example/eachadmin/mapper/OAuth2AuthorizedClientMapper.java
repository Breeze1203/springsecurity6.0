package com.example.eachadmin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2AuthorizedClientMapper{
    // 使用 principal.getName() 作为 principalName
    OAuth2AuthorizedClient selectAuthorizedClient(@Param("clientRegistrationId") String clientRegistrationId, @Param("principalName")String principalName);

    void updateAuthorizedClient(OAuth2AuthorizedClient authorizedClient);

    void insertAuthorizedClient(OAuth2AuthorizedClient authorizedClient);
}
