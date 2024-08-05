package com.example.eachadmin.config.cors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.Collections;

public class CustomizeCorsConfig implements Customizer<CorsConfigurer<HttpSecurity>> {

    @Override
    public void customize(CorsConfigurer<HttpSecurity> corsConfigurer) {
        corsConfigurer.configurationSource(new CustomizeCorsConfigurationSource());
    }

    public static class CustomizeCorsConfigurationSource implements CorsConfigurationSource {

        @Override
        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setMaxAge(3600L);
            return config;
        }
    }
}
