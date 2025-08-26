package com.inditex.priceapp.application;

import com.inditex.priceapp.domain.model.Price;
import com.inditex.priceapp.domain.model.policy.PriceSelectionPolicy;
import com.inditex.priceapp.domain.port.PriceRepositoryPort;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PriceService {
    private final PriceRepositoryPort repository;
    private final PriceSelectionPolicy selectionPolicy;

    public PriceService(PriceRepositoryPort repository, PriceSelectionPolicy selectionPolicy) {
        this.repository = repository;
        this.selectionPolicy = selectionPolicy;
    }

    public Optional<Price> getApplicablePrice(Long brandId, Long productId, LocalDateTime date) {
        // Intento eficiente (DB) si el adapter lo soporta:
        Optional<Price> direct = repository.findBestCandidate(brandId, productId, date);
        if (direct.isPresent()) return direct;

        // Fallback genérico (hexagonal puro):
        List<Price> candidates = repository.findCandidates(brandId, productId, date);
        return candidates.stream().sorted(selectionPolicy.comparator()).findFirst();
    }
}
