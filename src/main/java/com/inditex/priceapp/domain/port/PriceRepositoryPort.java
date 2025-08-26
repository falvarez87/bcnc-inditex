package com.inditex.priceapp.domain.port;

import com.inditex.priceapp.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceRepositoryPort {

    /**
     * Devuelve candidatos (filtro por brand, product y pertenencia al rango de fecha).
     */
    List<Price> findCandidates(Long brandId, Long productId, LocalDateTime applicationDate);

    /**
     * (Optimización) Devuelve directamente el mejor candidato según la política.
     * Un adapter puede usar ORDER BY y limit/paginación en la DB.
     */
    Optional<Price> findBestCandidate(Long brandId, Long productId, LocalDateTime applicationDate);
}