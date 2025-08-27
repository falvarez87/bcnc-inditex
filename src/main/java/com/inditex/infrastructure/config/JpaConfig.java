package com.inditex.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Represents the JPA Configuration file.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "com.inditex.infrastructure.persistence",
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager")
public class JpaConfig {}
