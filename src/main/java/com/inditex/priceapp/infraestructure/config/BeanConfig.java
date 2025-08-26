package com.inditex.priceapp.infraestructure.config;

import com.inditex.priceapp.application.PriceService;
import com.inditex.priceapp.domain.model.policy.DefaultPriceSelectionPolicy;
import com.inditex.priceapp.domain.model.policy.PriceSelectionPolicy;
import com.inditex.priceapp.domain.port.PriceRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public PriceSelectionPolicy priceSelectionPolicy() {
        return new DefaultPriceSelectionPolicy();
    }

    @Bean
    public PriceService priceService(PriceRepositoryPort repositoryPort,
                                     PriceSelectionPolicy selectionPolicy) {
        return new PriceService(repositoryPort, selectionPolicy);
    }
}
