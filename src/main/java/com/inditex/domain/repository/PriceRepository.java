package com.inditex.domain.repository;

import com.inditex.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
  Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
