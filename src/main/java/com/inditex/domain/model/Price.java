package com.inditex.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a product price in the domain model with business logic and validation. Contains all
 * price-related information including validity periods, priority rules, and pricing details.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price {
  private Long id;
  private Long brandId;
  private Long productId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Integer priceList;
  private Integer priority;
  private BigDecimal price;
  private String currency;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Price price = (Price) o;
    return Objects.equals(brandId, price.brandId)
        && Objects.equals(productId, price.productId)
        && Objects.equals(priceList, price.priceList)
        && Objects.equals(startDate, price.startDate)
        && Objects.equals(endDate, price.endDate);
  }
}
