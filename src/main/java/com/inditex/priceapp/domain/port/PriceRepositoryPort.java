package com.inditex.priceapp.domain.port;

import com.inditex.priceapp.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {

    List<Price> findCandidates(Long brandId, Long productId, LocalDateTime applicationDate);
}
