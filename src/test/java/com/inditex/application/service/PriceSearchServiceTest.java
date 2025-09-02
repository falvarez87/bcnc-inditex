package com.inditex.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.inditex.domain.model.Price;
import com.inditex.domain.repository.PriceRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceSearchServiceTest {

  @Mock private PriceRepository priceRepository;

  @InjectMocks private PriceSearchService priceSearchService;

  @Test
  void findApplicablePrice_WithValidParams_ReturnsPrice() {
    // Given
    Long brandId = 1L;
    Long productId = 35455L;
    LocalDateTime date = LocalDateTime.now();
    Price expectedPrice = Price.builder().build();

    when(priceRepository.findApplicablePrice(date, productId, brandId))
        .thenReturn(Optional.of(expectedPrice));

    // When
    Optional<Price> result = priceSearchService.findApplicablePrice(date, productId, brandId);

    // Then
    assertTrue(result.isPresent());
    assertEquals(expectedPrice, result.get());
    verify(priceRepository).findApplicablePrice(date, productId, brandId);
  }

  @Test
  void findApplicablePrice_WithNoResult_ReturnsEmpty() {
    // Given
    when(priceRepository.findApplicablePrice(any(), any(), any())).thenReturn(Optional.empty());

    // When
    Optional<Price> result =
        priceSearchService.findApplicablePrice(LocalDateTime.now(), 35455L, 1L);

    // Then
    assertFalse(result.isPresent());
    verify(priceRepository).findApplicablePrice(any(), any(), any());
  }

  @Test
  void findApplicablePrice_WithNullParams_ReturnsEmpty() {
    // When
    Optional<Price> result = priceSearchService.findApplicablePrice(null, null, null);

    // Then
    assertFalse(result.isPresent());
  }

  @Test
  void findApplicablePrice_WithNullDate_ReturnsEmpty() {
    // When
    Optional<Price> result = priceSearchService.findApplicablePrice(null, 35455L, 1L);

    // Then
    assertFalse(result.isPresent());
  }
}
