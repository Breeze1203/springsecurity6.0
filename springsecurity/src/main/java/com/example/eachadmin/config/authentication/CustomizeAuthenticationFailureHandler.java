package com.example.eachadmin.config.authentication;

import com.example.eachadmin.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.io.IOException;


public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static CustomizeAuthenticationFailureHandler instance;

    public static CustomizeAuthenticationFailureHandler getInstance() {
        if (instance == null) {
            instance = new CustomizeAuthenticationFailureHandler();
        }
        return instance;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Constants.loginFailureShare(request,response,exception);
    }

}
