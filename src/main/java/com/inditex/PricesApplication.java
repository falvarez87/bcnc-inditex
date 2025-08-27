package com.inditex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for Inditex Prices API. Bootstraps the application and
 * configures Spring context.
 */
@SpringBootApplication
public class PricesApplication {
  public static void main(String[] args) {
    SpringApplication.run(PricesApplication.class, args);
  }
}
