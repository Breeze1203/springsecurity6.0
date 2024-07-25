package com.example.eachadmin.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository(value = "RememberTokenMapper")
public interface TokenMapper {
    void createToken(PersistentRememberMeToken token);

    PersistentRememberMeToken getTokenBySeries(String series);

    void updateUserToken(@Param("series") String series, @Param("tokenValue")String tokenValue, @Param("lastUsed")Date lastUsed);

    void removeUserToken(String username);
}
