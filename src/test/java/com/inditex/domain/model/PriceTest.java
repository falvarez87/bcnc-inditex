package com.inditex.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PriceTest {

  @Test
  void equals_WithSameBusinessKey_ShouldReturnTrue() {
    Price price1 =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priceList(1)
            .priority(0)
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    Price price2 =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priceList(1)
            .priority(0)
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    assertEquals(price1, price2);
    assertEquals(price1.hashCode(), price2.hashCode());
  }

  @Test
  void equals_WithDifferentBrandId_ShouldReturnFalse() {
    Price price1 = Price.builder().brandId(1L).productId(35455L).build();
    Price price2 = Price.builder().brandId(2L).productId(35455L).build();

    assertNotEquals(price1, price2);
  }

  @Test
  void equals_WithDifferentProductId_ShouldReturnFalse() {
    Price price1 = Price.builder().brandId(1L).productId(35455L).build();
    Price price2 = Price.builder().brandId(1L).productId(99999L).build();

    assertNotEquals(price1, price2);
  }

  @Test
  void equals_WithDifferentDateRange_ShouldReturnFalse() {
    Price price1 =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 6, 14, 23, 59, 59))
            .build();

    Price price2 =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .startDate(LocalDateTime.of(2020, 6, 15, 0, 0))
            .endDate(LocalDateTime.of(2020, 6, 15, 23, 59, 59))
            .build();

    assertNotEquals(price1, price2);
  }

  @Test
  void equals_WithNull_ShouldReturnFalse() {
    Price price = Price.builder().brandId(1L).productId(35455L).build();
    assertNotEquals(null, price);
  }

  @Test
  void equals_WithDifferentClass_ShouldReturnFalse() {
    Price price = Price.builder().brandId(1L).productId(35455L).build();
    assertNotEquals("not-a-price", price);
  }

  @Test
  void hashCode_WithSameBusinessKey_ShouldBeEqual() {
    Price price1 = Price.builder().brandId(1L).productId(35455L).build();
    Price price2 = Price.builder().brandId(1L).productId(35455L).build();

    assertEquals(price1.hashCode(), price2.hashCode());
  }

  @Test
  void toString_ShouldContainImportantFields() {
    Price price =
        Price.builder()
            .brandId(1L)
            .productId(35455L)
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    String toString = price.toString();
    assertTrue(toString.contains("brandId=1"));
    assertTrue(toString.contains("productId=35455"));
    assertTrue(toString.contains("price=35.50"));
    assertTrue(toString.contains("currency=EUR"));
  }

  @Test
  void builder_ShouldCreateObjectWithAllFields() {
    Price price =
        Price.builder()
            .id(1L)
            .brandId(1L)
            .productId(35455L)
            .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
            .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
            .priceList(1)
            .priority(0)
            .price(new BigDecimal("35.50"))
            .currency("EUR")
            .build();

    assertNotNull(price);
    assertEquals(1L, price.getId());
    assertEquals(1L, price.getBrandId());
    assertEquals(35455L, price.getProductId());
    assertEquals(new BigDecimal("35.50"), price.getPrice());
    assertEquals("EUR", price.getCurrency());
  }
}
