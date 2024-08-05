package com.example.eachadmin.config.remember;

import com.example.eachadmin.mapper.TokenMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class CustomizeTokenRepository implements PersistentTokenRepository {

    private final TokenMapper tokenMapper;

    public CustomizeTokenRepository(@Qualifier("RememberTokenMapper") TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        tokenMapper.createToken(token);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        tokenMapper.updateUserToken(series, tokenValue, lastUsed);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return tokenMapper.getTokenBySeries(seriesId);
    }

    @Override
    public void removeUserTokens(String username) {
        if (username != null) {
            removeUserTokens(username);
        }
    }
}
