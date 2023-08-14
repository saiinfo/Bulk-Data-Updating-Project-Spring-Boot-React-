package com.bulkupdating.bulkupdating.conroller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Adjust the mapping based on your API endpoints
            .allowedOrigins("http://localhost:3000")  // Allow requests from your React app's domain
            .allowedMethods("GET", "POST"); // Allow specific HTTP methods
    }
}