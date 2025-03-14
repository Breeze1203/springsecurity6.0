package com.example.eachadmin.config.authentication.provider;

import com.example.eachadmin.config.authentication.provider.sms.SmsAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SmsAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService; // 用于加载用户信息

    @Autowired
    private RedisTemplate<String, Object> redisTemplate; // 用于验证短信验证码

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 转换为 SmsAuthToken
        SmsAuthToken smsToken = (SmsAuthToken) authentication;
        // 获取手机号和验证码
        String phoneNumber = (String) smsToken.getPrincipal();
        String smsCode = (String) smsToken.getCredentials();
        // 从 Redis 中获取存储的验证码
        String storedCode = (String) redisTemplate.opsForValue().get("sms_code:" + phoneNumber);
        // 验证验证码
        if (storedCode == null || !storedCode.equals(smsCode)) {
            throw new BadCredentialsException("Invalid SMS code for phone number: " + phoneNumber);
        }
        // 验证码验证通过后，加载用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);
        if (userDetails == null) {
            throw new BadCredentialsException("User not found for phone number: " + phoneNumber);
        }
        // 创建认证成功的令牌
        SmsAuthToken authenticatedToken = new SmsAuthToken(
                phoneNumber, smsCode, userDetails.getAuthorities()
        );
        authenticatedToken.setDetails(authentication.getDetails()); // 保留认证细节
        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 只支持 SmsAuthToken 类型
        return SmsAuthToken.class.isAssignableFrom(authentication);
    }
}