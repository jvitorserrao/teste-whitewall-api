package com.springboot.webclient_blip_api.api.controller.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://localhost:3000", "http://localhost:3000/contatos")
                        .allowedMethods("POST")
                        .allowCredentials(true)
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization");
            }
        };
    }
}