package com.example.eachadmin.config.oauth.login;

import com.example.eachadmin.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.io.IOException;


public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static OAuth2AuthenticationFailureHandler instance;

    public static OAuth2AuthenticationFailureHandler getInstance() {
        if (instance == null) {
            instance = new OAuth2AuthenticationFailureHandler();
        }
        return instance;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Constants.loginFailureShare(request,response,exception);
    }

}
