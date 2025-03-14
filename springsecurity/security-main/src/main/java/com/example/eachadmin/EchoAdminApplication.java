package com.example.eachadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.eachadmin.mapper")
@SpringBootApplication
public class EchoAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoAdminApplication.class, args);
    }

}
