package com.sistemaclinica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permitir todas as origens (em desenvolvimento)
        config.addAllowedOrigin("*");
        
        // Ou origens específicas
        config.addAllowedOrigin("http://localhost:5173");
        // config.addAllowedOrigin("http://localhost:3000");
        
        // Métodos permitidos
        config.addAllowedMethod("*"); // GET, POST, PUT, DELETE, etc.
        
        // Headers permitidos
        config.addAllowedHeader("*");
        
        // Permitir credenciais (cookies, auth)
        config.setAllowCredentials(false);
        
        // Tempo que a configuração pode ser cacheada (em segundos)
        config.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
} 