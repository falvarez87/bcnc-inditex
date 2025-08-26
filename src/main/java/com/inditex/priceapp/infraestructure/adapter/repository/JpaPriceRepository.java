package com.inditex.priceapp.infraestructure.adapter.repository;

import com.inditex.priceapp.domain.model.Price;
import com.inditex.priceapp.domain.port.PriceRepositoryPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaPriceRepository implements PriceRepositoryPort {

    private final SpringDataPriceRepository jpa;

    public JpaPriceRepository(SpringDataPriceRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Price> findCandidates(Long brandId, Long productId, LocalDateTime applicationDate) {
        return jpa.findCandidates(brandId, productId, applicationDate)
                .stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Price> findBestCandidate(Long brandId, Long productId, LocalDateTime applicationDate) {
        return jpa.findOrdered(brandId, productId, applicationDate, PageRequest.of(0, 1))
                .stream().findFirst().map(this::toDomain);
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

