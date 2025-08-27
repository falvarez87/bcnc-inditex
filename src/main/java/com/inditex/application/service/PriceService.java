package com.inditex.application.service;

import com.inditex.application.dto.PriceResponse;
import com.inditex.domain.exception.PriceNotFoundException;
import com.inditex.domain.model.Price;
import com.inditex.domain.repository.PriceRepository;
import java.time.LocalDateTime;
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

  private final PriceRepository priceRepository;

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
    return priceRepository
        .findApplicablePrice(applicationDate, productId, brandId)
        .map(this::convertToResponse)
        .orElseThrow(
            () ->
                new PriceNotFoundException(
                    String.format(
                        "No price found for date: %s, productId: %d, brandId: %d",
                        applicationDate, productId, brandId)));
  }

  private PriceResponse convertToResponse(Price price) {
    return new PriceResponse(
        price.getProductId(),
        price.getBrandId(),
        price.getPriceList(),
        price.getStartDate(),
        price.getEndDate(),
        price.getPrice(),
        price.getCurrency());
  }
}
