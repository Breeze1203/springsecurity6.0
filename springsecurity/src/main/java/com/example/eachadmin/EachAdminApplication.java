package com.example.eachadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan(basePackages = "com.example.eachadmin.mapper")
@SpringBootApplication
public class EachAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EachAdminApplication.class, args);
    }

}
