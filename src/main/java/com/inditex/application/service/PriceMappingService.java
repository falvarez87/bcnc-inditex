package com.inditex.application.service;

import com.inditex.application.dto.PriceResponse;
import com.inditex.domain.exception.PriceNotFoundException;
import com.inditex.domain.model.Price;
import org.springframework.stereotype.Service;

/**
 * Service for handle the Price service response.
 */
@Service
public class PriceMappingService {

  /**
   * Retrieves the PriceResponse map from price domain.
   *
   * @param price the date and time for price request
   * @return PriceResponse with applicable price details
   */
  public PriceResponse toResponse(Price price) {
    if (price == null) {
      return null;
    }

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
