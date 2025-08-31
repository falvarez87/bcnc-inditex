package com.inditex.infrastructure.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.inditex.domain.model.Price;
import com.inditex.infrastructure.persistence.entity.PriceEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link PriceMapper} class. Tests the mapping functionality between PriceEntity and
 * Price domain objects.
 */
@ExtendWith(MockitoExtension.class)
class PriceMapperTest {

  @InjectMocks private PriceMapper priceMapper;

  @BeforeEach
  void setUp() {
    priceMapper = new PriceMapper();
  }

  @Test
  void toDomain_WithValidEntity_ReturnsCorrectDomainObject() {
    // Given
    PriceEntity entity = createPriceEntity();

    // When
    Price result = priceMapper.toDomain(entity);

    // Then
    assertNotNull(result);
    assertEquals(entity.getBrandId(), result.getBrandId());
    assertEquals(entity.getProductId(), result.getProductId());
    assertEquals(entity.getPriceList(), result.getPriceList());
    assertEquals(entity.getStartDate(), result.getStartDate());
    assertEquals(entity.getEndDate(), result.getEndDate());
    assertEquals(entity.getPrice(), result.getPrice());
    assertEquals(entity.getCurrency(), result.getCurrency());
  }

  @Test
  void toDomain_WithNullEntity_ReturnsNull() {
    // When
    Price result = priceMapper.toDomain(null);

    // Then
    assertNull(result);
  }

  @Test
  void toDomain_WithEntityHavingNullValues_ReturnsDomainWithNullValues() {
    // Given
    PriceEntity entity = new PriceEntity();
    entity.setBrandId(1L);
    entity.setProductId(35455L);
    // Other fields remain null

    // When
    Price result = priceMapper.toDomain(entity);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getBrandId());
    assertEquals(35455L, result.getProductId());
    assertNull(result.getPriceList());
    assertNull(result.getStartDate());
    assertNull(result.getEndDate());
    assertNull(result.getPrice());
    assertNull(result.getCurrency());
  }

  @Test
  void toEntity_WithValidDomain_ReturnsCorrectEntity() {
    // Given
    Price domain = createPriceDomain();

    // When
    PriceEntity result = priceMapper.toEntity(domain);

    // Then
    assertNotNull(result);
    assertEquals(domain.getBrandId(), result.getBrandId());
    assertEquals(domain.getProductId(), result.getProductId());
    assertEquals(domain.getPriceList(), result.getPriceList());
    assertEquals(domain.getStartDate(), result.getStartDate());
    assertEquals(domain.getEndDate(), result.getEndDate());
    assertEquals(domain.getPrice(), result.getPrice());
    assertEquals(domain.getCurrency(), result.getCurrency());
  }

  @Test
  void toEntity_WithNullDomain_ReturnsNull() {
    // When
    PriceEntity result = priceMapper.toEntity(null);

    // Then
    assertNull(result);
  }

  @Test
  void toEntity_WithDomainHavingNullValues_ReturnsEntityWithNullValues() {
    // Given
    Price domain =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            // Other fields remain null
            .build();

    // When
    PriceEntity result = priceMapper.toEntity(domain);

    // Then
    assertNotNull(result);
    assertEquals(1L, result.getBrandId());
    assertEquals(35455L, result.getProductId());
    assertNull(result.getPriceList());
    assertNull(result.getStartDate());
    assertNull(result.getEndDate());
    assertNull(result.getPrice());
    assertNull(result.getCurrency());
  }

  private PriceEntity createPriceEntity() {
    PriceEntity entity = new PriceEntity();
    entity.setBrandId(1L);
    entity.setProductId(35455L);
    entity.setPriceList(1);
    entity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
    entity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
    entity.setPrice(new BigDecimal("35.50"));
    entity.setCurrency("EUR");
    return entity;
  }

  private Price createPriceDomain() {
    return Price.builder()
        .brandId(1L)
        .productId(35455L)
        .priceList(1)
        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
        .price(new BigDecimal("35.50"))
        .currency("EUR")
        .build();
  }
}
