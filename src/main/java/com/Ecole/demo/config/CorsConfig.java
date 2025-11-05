package com.Ecole.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed.origins:http://localhost:3000,http://localhost:5173,http://localhost:8080}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow credentials
        config.setAllowCredentials(true);
        
        // Convert comma-separated string to list and use allowedOriginPatterns
        String[] origins = allowedOrigins.split(",");
        for (String origin : origins) {
            config.addAllowedOriginPattern(origin.trim());
        }
        
        // Allow all headers
        config.addAllowedHeader("*");
        
        // Allow all methods
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
        
        // Set max age
        config.setMaxAge(3600L);
        
        // Apply CORS configuration to all paths
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = allowedOrigins.split(",");
        String[] trimmedOrigins = new String[origins.length];
        for (int i = 0; i < origins.length; i++) {
            trimmedOrigins[i] = origins[i].trim();
        }
        
        registry.addMapping("/**")
                .allowedOriginPatterns(trimmedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
