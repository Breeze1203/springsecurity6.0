package com.example.eachadmin.config.authentication.provider.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsAuthToken extends AbstractAuthenticationToken {
    private final String phoneNumber; // 手机号作为 principal
    private final String smsCode;     // 验证码作为 credentials

    // 未认证时的构造方法
    public SmsAuthToken(String phoneNumber, String smsCode) {
        super(null);
        this.phoneNumber = phoneNumber;
        this.smsCode = smsCode;
        setAuthenticated(false); // 初始化为未认证状态
    }

    // 认证成功后的构造方法
    public SmsAuthToken(String phoneNumber, String smsCode, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.phoneNumber = phoneNumber;
        this.smsCode = smsCode;
        setAuthenticated(true); // 已认证状态
    }

    @Override
    public Object getCredentials() {
        return smsCode; // 返回验证码
    }

    @Override
    public Object getPrincipal() {
        return phoneNumber; // 返回手机号
    }
}