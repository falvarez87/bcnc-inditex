package com.inditex.priceapp.infraestructure.adapter.repository;

import com.inditex.priceapp.domain.model.Price;
import com.inditex.priceapp.domain.port.PriceRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaPriceRepository implements PriceRepositoryPort {

    private final SpringDataPriceRepository jpa;

    public JpaPriceRepository(SpringDataPriceRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Price> findCandidates(Long brandId, Long productId, LocalDateTime applicationDate) {
        return jpa.findCandidates(brandId, productId, applicationDate)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private Price toDomain(PriceEntity e) {
        return new Price(
                e.getBrandId(),
                e.getStartDate(),
                e.getEndDate(),
                e.getPriceList(),
                e.getProductId(),
                e.getPriority(),
                e.getPrice(),
                e.getCurrency()
        );
    }
}

