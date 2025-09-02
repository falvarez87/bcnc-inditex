package com.inditex.application.service;

import com.inditex.application.dto.PriceResponse;
import com.inditex.domain.exception.PriceNotFoundException;
import com.inditex.domain.model.Price;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer component responsible for price-related business operations. Handles the core
 * business logic for retrieving and processing product prices.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PriceService {

  private final PriceSearchService priceSearchService;
  private final PriceMappingService priceMappingService;

  /**
   * Retrieves the applicable price for a product based on the specified criteria.
   *
   * @param applicationDate the date and time for price request
   * @param productId the product identifier
   * @param brandId the brand identifier
   * @return PriceResponse with applicable price details
   * @throws PriceNotFoundException if no applicable price is found
   */
  @Transactional(readOnly = true)
  public PriceResponse getApplicablePrice(
      LocalDateTime applicationDate, Long productId, Long brandId) {
    Optional<Price> price =
        priceSearchService.findApplicablePrice(applicationDate, productId, brandId);

    return price
        .map(priceMappingService::toResponse)
        .orElseThrow(
            () ->
                new PriceNotFoundException(
                    "Price not found for brandId: "
                        + brandId
                        + ", productId: "
                        + productId
                        + ", date: "
                        + applicationDate));
  }
}
