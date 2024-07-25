package com.example.eachadmin.config.Authentication;

import com.example.eachadmin.entity.User;
import com.example.eachadmin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


/*
暴露UserDetailService
 */
@Service
public class CustomizeUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("not found for the user : " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(username,user.getPwd(), Collections.emptyList());
        }
    }
}
