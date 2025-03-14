package com.example.eachadmin.config.authentication;

import com.example.eachadmin.config.authorization.PerService;
import com.example.eachadmin.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public final class AuthSuccess implements AuthenticationSuccessHandler {

    private final PerService perService;


    public AuthSuccess(PerService perService) {
        this.perService = perService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Constants.loginSuccessShare(request,response,authentication);
        perService.getAllowedUrlsForUser(authentication.getName());
    }
}
