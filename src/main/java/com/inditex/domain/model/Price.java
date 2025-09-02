package com.inditex.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@EqualsAndHashCode
@ToString
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
}
