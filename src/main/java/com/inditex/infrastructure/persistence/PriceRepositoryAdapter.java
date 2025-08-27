package com.inditex.infrastructure.persistence;

import com.inditex.domain.model.Price;
import com.inditex.domain.repository.PriceRepository;
import com.inditex.infrastructure.persistence.mapper.PriceMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Represents the adapter for PriceRepository.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
@Component
public class PriceRepositoryAdapter implements PriceRepository {

  private final JpaPriceRepository jpaPriceRepository;
  private final PriceMapper priceMapper;

  public PriceRepositoryAdapter(JpaPriceRepository jpaPriceRepository, PriceMapper priceMapper) {
    this.jpaPriceRepository = jpaPriceRepository;
    this.priceMapper = priceMapper;
  }

  @Override
  public Optional<Price> findApplicablePrice(
      LocalDateTime applicationDate, Long productId, Long brandId) {
    return jpaPriceRepository
        .findApplicablePrice(applicationDate, productId, brandId)
        .map(priceMapper::toDomain);
  }
}
