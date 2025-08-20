package com.inditex.priceapp.infraestructure.config;

import com.inditex.priceapp.application.PriceService;
import com.inditex.priceapp.domain.port.PriceRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public PriceService priceService(PriceRepositoryPort repositoryPort) {
        return new PriceService(repositoryPort);
    }
}
