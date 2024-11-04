package com.product_trial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Main application class for the Product Trial Backend.
 * This class serves as the entry point for the Spring Boot application.
 *
 */
@ConfigurationPropertiesScan
@SpringBootApplication
public class ProductTrialBack {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args command line arguments passed during application startup
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductTrialBack.class, args);
    }
}
