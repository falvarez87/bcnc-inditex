package com.inditex.application.service;

import com.inditex.domain.model.Price;
import com.inditex.domain.repository.PriceRepository;
import com.inditex.application.dto.PriceResponse;
import com.inditex.domain.exception.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @Test
    void test1_10amDay14_ShouldReturnPriceList1() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = new Price(1L, brandId,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1, productId, 0, new BigDecimal("35.50"), "EUR");

        when(priceRepository.findApplicablePrice(date, productId, brandId))
                .thenReturn(Optional.of(price));

        // When
        PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

        // Then
        assertNotNull(result);
        assertEquals(productId, result.productId());
        assertEquals(brandId, result.brandId());
        assertEquals(1, result.priceList());
        assertEquals(new BigDecimal("35.50"), result.price());
        assertEquals("EUR", result.currency());

        verify(priceRepository).findApplicablePrice(date, productId, brandId);
    }

    @Test
    void test2_4pmDay14_ShouldReturnPriceList2() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = new Price(2L, brandId,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                2, productId, 1, new BigDecimal("25.45"), "EUR");

        when(priceRepository.findApplicablePrice(date, productId, brandId))
                .thenReturn(Optional.of(price));

        // When
        PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

        // Then
        assertEquals(2, result.priceList());
        assertEquals(new BigDecimal("25.45"), result.price());
    }

    @Test
    void test3_9pmDay14_ShouldReturnPriceList1() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 21, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = new Price(1L, brandId,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                1, productId, 0, new BigDecimal("35.50"), "EUR");

        when(priceRepository.findApplicablePrice(date, productId, brandId))
                .thenReturn(Optional.of(price));

        // When
        PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

        // Then
        assertEquals(1, result.priceList());
        assertEquals(new BigDecimal("35.50"), result.price());
    }

    @Test
    void test4_10amDay15_ShouldReturnPriceList3() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 15, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = new Price(3L, brandId,
                LocalDateTime.of(2020, 6, 15, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0),
                3, productId, 1, new BigDecimal("30.50"), "EUR");

        when(priceRepository.findApplicablePrice(date, productId, brandId))
                .thenReturn(Optional.of(price));

        // When
        PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

        // Then
        assertEquals(3, result.priceList());
        assertEquals(new BigDecimal("30.50"), result.price());
    }

    @Test
    void test5_9pmDay16_ShouldReturnPriceList4() {
        // Given
        LocalDateTime date = LocalDateTime.of(2020, 6, 16, 21, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = new Price(4L, brandId,
                LocalDateTime.of(2020, 6, 15, 16, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                4, productId, 1, new BigDecimal("38.95"), "EUR");

        when(priceRepository.findApplicablePrice(date, productId, brandId))
                .thenReturn(Optional.of(price));

        // When
        PriceResponse result = priceService.getApplicablePrice(date, productId, brandId);

        // Then
        assertEquals(4, result.priceList());
        assertEquals(new BigDecimal("38.95"), result.price());
    }

    @Test
    void shouldThrowExceptionWhenPriceNotFound() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        Long productId = 999L;
        Long brandId = 1L;

        when(priceRepository.findApplicablePrice(date, productId, brandId))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(PriceNotFoundException.class, () -> {
            priceService.getApplicablePrice(date, productId, brandId);
        });

        verify(priceRepository).findApplicablePrice(date, productId, brandId);
    }
}