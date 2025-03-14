package com.example.eachadmin.config.authentication.provider.sms;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * @ClassName SmsAuthFilter
 * @Author pt
 * @Description
 * @Date 2025/3/14 10:30
 **/

public class SmsAuthFilter extends AbstractAuthenticationProcessingFilter {

    public SmsAuthFilter() {
        super(new AntPathRequestMatcher("/sms-login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String phoneNumber = request.getParameter("phoneNumber");
        String smsCode = request.getParameter("smsCode");

        SmsAuthToken token = new SmsAuthToken(phoneNumber, smsCode);
        return this.getAuthenticationManager().authenticate(token);
    }
}
