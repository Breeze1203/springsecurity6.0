package com.example.eachadmin.config.remember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.stereotype.Component;

@Component
public class RememberConfig implements Customizer<RememberMeConfigurer<HttpSecurity>>{
    @Autowired
    private RememberTokenRepository rememberTokenRepository;


    @Override
    public void customize(RememberMeConfigurer<HttpSecurity> httpSecurityRememberMeConfigurer) {
       httpSecurityRememberMeConfigurer.rememberMeParameter("remember") //标识记住我功能的参数名或者请求参数名
                .tokenRepository(rememberTokenRepository)
                .rememberMeCookieDomain("localhost")//存储库负责存储生成的令牌
                .tokenValiditySeconds(5*60*60);//设置生成的记住我令牌的有效时间
                //.RememberService(RememberService);//处理记住我功能的核心逻辑););//处理记住我功能的核心逻辑);
    }
}
