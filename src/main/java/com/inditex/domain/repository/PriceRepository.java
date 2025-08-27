package com.inditex.domain.repository;

import com.inditex.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Represents an interface for PriceRepository included the method for FindApplicablePrice.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
public interface PriceRepository {
  Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
