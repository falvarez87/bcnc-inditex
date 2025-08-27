package com.inditex.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

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

  /** Determines if this price is applicable for the given date. */
  public boolean isApplicable(LocalDateTime applicationDate) {
    return !applicationDate.isBefore(startDate) && !applicationDate.isAfter(endDate);
  }

  /** Compares priorities between two price entries. */
  public int comparePriority(Price other) {
    return Integer.compare(this.priority, other.priority);
  }
}
