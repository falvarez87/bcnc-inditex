package com.inditex.application.service;

import com.inditex.domain.exception.PriceNotFoundException;
import com.inditex.domain.model.Price;
import com.inditex.domain.repository.PriceRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service layer for handle the Price Service search to repository.
 */
@Service
@RequiredArgsConstructor
public class PriceSearchService {

  private final PriceRepository priceRepository;

  /**
   * Retrieves the applicable price for a product based on the specified criteria.
   *
   * @param date the date and time for price request
   * @param productId the product identifier
   * @param brandId the brand identifier
   * @return PriceResponse with applicable price details
   * @throws PriceNotFoundException if no applicable price is found
   */
  public Optional<Price> findApplicablePrice(LocalDateTime date, Long productId, Long brandId) {
    return priceRepository.findApplicablePrice(date, productId, brandId);
  }
}
