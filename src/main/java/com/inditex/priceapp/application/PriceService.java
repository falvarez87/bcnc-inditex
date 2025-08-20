package com.inditex.priceapp.application;

import com.inditex.priceapp.domain.model.Price;
import com.inditex.priceapp.domain.port.PriceRepositoryPort;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PriceService {
    private final PriceRepositoryPort repository;

    public PriceService(PriceRepositoryPort repository) {
        this.repository = repository;
    }

    public Optional<Price> getApplicablePrice(Long brandId, Long productId, LocalDateTime date) {
        List<Price> candidates = repository.findCandidates(brandId, productId, date);

        return candidates.stream()
                .sorted(Comparator
                        .comparingInt(Price::getPriority).reversed()
                        .thenComparing(Price::getStartDate, Comparator.reverseOrder()))
                .findFirst();
    }
}
