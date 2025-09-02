package com.inditex.application.service;

import static org.junit.jupiter.api.Assertions.*;

import com.inditex.application.dto.PriceResponse;
import com.inditex.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PriceMappingServiceTest {

  private final PriceMappingService priceMappingService = new PriceMappingService();

  @Test
  void toResponse_WithValidPrice_ReturnsResponse() {
    // Given
    Price price =
        Price.builder()
            .productId(35455L)
            .brandId(1L)
            .priceList(1)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    // When
    PriceResponse result = priceMappingService.toResponse(price);

    // Then
    assertNotNull(result);
    assertEquals(35455L, result.productId());
    assertEquals(1L, result.brandId());
    assertEquals(1, result.priceList());
    assertEquals(new BigDecimal("35.50"), result.price());
    assertEquals("EUR", result.currency());
  }

  @Test
  void toResponse_WithNullPrice_ReturnsNull() {
    // When
    PriceResponse result = priceMappingService.toResponse(null);

    // Then
    assertNull(result);
  }

  @Test
  void toResponse_WithPartialData_ReturnsResponse() {
    // Given
    Price price =
        Price.builder()
            .productId(35455L)
            .brandId(1L)
            // Other fields are null
            .build();

    // When
    PriceResponse result = priceMappingService.toResponse(price);

    // Then
    assertNotNull(result);
    assertEquals(35455L, result.productId());
    assertEquals(1L, result.brandId());
    assertNull(result.priceList());
    assertNull(result.startDate());
    assertNull(result.endDate());
    assertNull(result.price());
    assertNull(result.currency());
  }
}
