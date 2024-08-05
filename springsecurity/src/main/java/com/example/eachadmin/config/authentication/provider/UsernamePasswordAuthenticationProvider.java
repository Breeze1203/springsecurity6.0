package com.example.eachadmin.config.authentication.provider;

import com.example.eachadmin.entity.User;
import com.example.eachadmin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String pwd = authentication.getCredentials().toString();
        User user = userMapper.findUserByName(username);
        if (user != null) {
            if (bCryptPasswordEncoder.matches(pwd, user.getPwd())) {
                return new UsernamePasswordAuthenticationToken(username, pwd,Collections.emptyList());
            } else {
                throw new BadCredentialsException("密码错误!");
            }
        } else {
            throw new BadCredentialsException("该用户尚未注册");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
