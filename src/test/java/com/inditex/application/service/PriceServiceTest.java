package com.inditex.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.inditex.application.dto.PriceResponse;
import com.inditex.domain.exception.PriceNotFoundException;
import com.inditex.domain.model.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

  @Mock private PriceSearchService priceSearchService;
  @Mock private PriceMappingService priceMappingService;

  @InjectMocks private PriceService priceService;

  @Test
  void test1_10amDay14_ShouldReturnPriceList1() {
    // Given
    LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price price =
        Price.builder()
            .brandId(brandId)
            .productId(productId)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priceList(1)
            .priority(0)
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse(
            productId,
            brandId,
            1,
            LocalDateTime.of(2020, 6, 14, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59),
            new BigDecimal("35.50"),
            "EUR");

    when(priceSearchService.findApplicablePrice(date, productId, brandId))
        .thenReturn(Optional.of(price));
    when(priceMappingService.toResponse(price)).thenReturn(expectedResponse);

    // When
    PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

    // Then
    assertNotNull(result);
    assertEquals(productId, result.productId());
    assertEquals(brandId, result.brandId());
    assertEquals(1, result.priceList());
    assertEquals(new BigDecimal("35.50"), result.price());
    assertEquals("EUR", result.currency());

    verify(priceSearchService).findApplicablePrice(date, productId, brandId);
    verify(priceMappingService).toResponse(price);
  }

  @Test
  void test2_4pmDay14_ShouldReturnPriceList2() {
    // Given
    LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price price =
        Price.builder()
            .brandId(brandId)
            .productId(productId)
            .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
            .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
            .priceList(2)
            .priority(1)
            .price(new BigDecimal("25.45"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse(
            productId,
            brandId,
            2,
            LocalDateTime.of(2020, 6, 14, 15, 0),
            LocalDateTime.of(2020, 6, 14, 18, 30),
            new BigDecimal("25.45"),
            "EUR");

    when(priceSearchService.findApplicablePrice(date, productId, brandId))
        .thenReturn(Optional.of(price));
    when(priceMappingService.toResponse(price)).thenReturn(expectedResponse);

    // When
    PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

    // Then
    assertEquals(2, result.priceList());
    assertEquals(new BigDecimal("25.45"), result.price());

    verify(priceSearchService).findApplicablePrice(date, productId, brandId);
    verify(priceMappingService).toResponse(price);
  }

  @Test
  void test3_9pmDay14_ShouldReturnPriceList1() {
    // Given
    LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price price =
        Price.builder()
            .brandId(brandId)
            .productId(productId)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priceList(1)
            .priority(0)
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse(
            productId,
            brandId,
            1,
            LocalDateTime.of(2020, 6, 14, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59),
            new BigDecimal("35.50"),
            "EUR");

    when(priceSearchService.findApplicablePrice(date, productId, brandId))
        .thenReturn(Optional.of(price));
    when(priceMappingService.toResponse(price)).thenReturn(expectedResponse);

    // When
    PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

    // Then
    assertEquals(1, result.priceList());
    assertEquals(new BigDecimal("35.50"), result.price());

    verify(priceSearchService).findApplicablePrice(date, productId, brandId);
    verify(priceMappingService).toResponse(price);
  }

  @Test
  void test4_10amDay15_ShouldReturnPriceList3() {
    // Given
    LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price price =
        Price.builder()
            .brandId(brandId)
            .productId(productId)
            .startDate(LocalDateTime.of(2020, 6, 15, 0, 0))
            .endDate(LocalDateTime.of(2020, 6, 15, 11, 0))
            .priceList(3)
            .priority(1)
            .price(new BigDecimal("30.50"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse(
            productId,
            brandId,
            3,
            LocalDateTime.of(2020, 6, 15, 0, 0),
            LocalDateTime.of(2020, 6, 15, 11, 0),
            new BigDecimal("30.50"),
            "EUR");

    when(priceSearchService.findApplicablePrice(date, productId, brandId))
        .thenReturn(Optional.of(price));
    when(priceMappingService.toResponse(price)).thenReturn(expectedResponse);

    // When
    PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

    // Then
    assertEquals(3, result.priceList());
    assertEquals(new BigDecimal("30.50"), result.price());

    verify(priceSearchService).findApplicablePrice(date, productId, brandId);
    verify(priceMappingService).toResponse(price);
  }

  @Test
  void test5_9pmDay16_ShouldReturnPriceList4() {
    // Given
    LocalDateTime date = LocalDateTime.of(2020, 6, 16, 21, 0);
    Long productId = 35455L;
    Long brandId = 1L;

    Price price =
        Price.builder()
            .brandId(brandId)
            .productId(productId)
            .startDate(LocalDateTime.of(2020, 6, 15, 16, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priceList(4)
            .priority(1)
            .price(new BigDecimal("38.95"))
            .currency("EUR")
            .build();

    PriceResponse expectedResponse =
        new PriceResponse(
            productId,
            brandId,
            4,
            LocalDateTime.of(2020, 6, 15, 16, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59),
            new BigDecimal("38.95"),
            "EUR");

    when(priceSearchService.findApplicablePrice(date, productId, brandId))
        .thenReturn(Optional.of(price));
    when(priceMappingService.toResponse(price)).thenReturn(expectedResponse);

    // When
    PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

    // Then
    assertEquals(4, result.priceList());
    assertEquals(new BigDecimal("38.95"), result.price());

    verify(priceSearchService).findApplicablePrice(date, productId, brandId);
    verify(priceMappingService).toResponse(price);
  }

  @Test
  void shouldThrowExceptionWhenPriceNotFound() {
    // Given
    LocalDateTime date = LocalDateTime.now();
    Long productId = 999L;
    Long brandId = 1L;

    when(priceSearchService.findApplicablePrice(date, productId, brandId))
        .thenReturn(Optional.empty());

    // When & Then
    assertThrows(
        PriceNotFoundException.class,
        () -> priceService.getApplicablePrice(date, productId, brandId));

    verify(priceSearchService).findApplicablePrice(date, productId, brandId);
    verify(priceMappingService, never()).toResponse(any());
  }
}
