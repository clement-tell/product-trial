package com.product_trial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) in the application.
 * This class defines allowed origins, methods, and headers for cross-origin requests.
 */
@Configuration
public class CorsConfig {

    private static final String[] ALLOWED_ORIGIN = {
            "http://localhost:4200",
            "https://clement-tell-product-trial.com"
    };

    /**
     * Configures CORS settings for the application.
     *
     * @return a WebMvcConfigurer that adds CORS mappings.
     */
    @Bean
    public WebMvcConfigurer getCorsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(ALLOWED_ORIGIN)
                        .allowedMethods("GET", "POST", "PATCH", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}
