package com.inditex.infrastructure.persistence;

import com.inditex.infrastructure.persistence.entity.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for {@link JpaPriceRepository} class.
 * Tests the repository layer with H2 in-memory database.
 */
@DataJpaTest
@Sql(scripts = "/test-data.sql")
class JpaPriceRepositoryTest {

    @Autowired
    private JpaPriceRepository jpaPriceRepository;

    @Test
    void findApplicablePrice_WithValidCriteria_ReturnsPrice() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        // When - ¡ORDEN CORRECTO de parámetros!
        Optional<PriceEntity> result = jpaPriceRepository.findApplicablePrice(
                applicationDate, // ← Primero: LocalDateTime
                productId,       // ← Segundo: Long
                brandId          // ← Tercero: Long
        );

        // Then
        assertTrue(result.isPresent());
        PriceEntity price = result.get();
        assertEquals(brandId, price.getBrandId());
        assertEquals(productId, price.getProductId());
        assertEquals(1, price.getPriceList());
    }

    @Test
    void findApplicablePrice_WithHighestPriority_ReturnsHighestPriorityPrice() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        // When
        Optional<PriceEntity> result = jpaPriceRepository.findApplicablePrice(
                applicationDate, productId, brandId);

        // Then
        assertTrue(result.isPresent());
        // Should return price with higher priority (priceList 2)
        assertEquals(2, result.get().getPriceList());
    }

    @Test
    void findApplicablePrice_WithNonExistingProduct_ReturnsEmpty() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Long productId = 99999L; // Non-existing product
        Long brandId = 1L;

        // When
        Optional<PriceEntity> result = jpaPriceRepository.findApplicablePrice(
                applicationDate, productId, brandId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findApplicablePrice_WithNonExistingBrand_ReturnsEmpty() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Long productId = 35455L;
        Long brandId = 999L; // Non-existing brand

        // When
        Optional<PriceEntity> result = jpaPriceRepository.findApplicablePrice(
                applicationDate, productId, brandId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findApplicablePrice_WithDateOutsideRange_ReturnsEmpty() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2025, 1, 1, 10, 0, 0); // Future date
        Long productId = 35455L;
        Long brandId = 1L;

        // When
        Optional<PriceEntity> result = jpaPriceRepository.findApplicablePrice(
                applicationDate, productId, brandId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void findApplicablePrice_WithExactStartDate_ReturnsPrice() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 0, 0, 0); // Exact start date
        Long productId = 35455L;
        Long brandId = 1L;

        // When
        Optional<PriceEntity> result = jpaPriceRepository.findApplicablePrice(
                applicationDate, productId, brandId);

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void findApplicablePrice_WithExactEndDate_ReturnsPrice() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59); // Exact end date
        Long productId = 35455L;
        Long brandId = 1L;

        // When
        Optional<PriceEntity> result = jpaPriceRepository.findApplicablePrice(
                applicationDate, productId, brandId);

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    void findApplicablePrice_WithTimeAtStartOfDay_ReturnsPrice() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 0, 0, 0); // Start of day
        Long productId = 35455L;
        Long brandId = 1L;

        // When
        Optional<PriceEntity> result = jpaPriceRepository.findApplicablePrice(
                applicationDate, productId, brandId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getPriceList()); // Should be price list 3
    }

    @Test
    void findApplicablePrice_WithTimeAtEndOfDay_ReturnsPrice() {
        // Given
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 11, 0, 0); // End of price list 3
        Long productId = 35455L;
        Long brandId = 1L;

        // When
        Optional<PriceEntity> result = jpaPriceRepository.findApplicablePrice(
                applicationDate, productId, brandId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(3, result.get().getPriceList()); // Should be price list 3
    }
}