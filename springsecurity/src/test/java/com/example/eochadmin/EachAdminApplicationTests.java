package com.example.eochadmin;

import com.example.eachadmin.config.remember.CustomizeRememberMeServices;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
class EachAdminApplicationTests {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("12345"));
        String[] a = CustomizeRememberMeServices.decodeCookie("QnV4Q1JxTEtVQzVZeWUyY2tPUDZmUSUzRCUzRDp1bFp6Q3dPdG9mS1Q4bG4wekN4eUJBJTNEJTNE");
        System.out.println(a[0]);
        System.out.println(a[1]);
    }

    @Test
    void contextLoads() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("12345"));
    }

}
