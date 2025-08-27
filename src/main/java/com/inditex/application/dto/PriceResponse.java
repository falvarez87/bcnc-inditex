package com.inditex.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a record PriceResponse for controller , with all the pricing details.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
public record PriceResponse(
    Long productId,
    Long brandId,
    Integer priceList,
    LocalDateTime startDate,
    LocalDateTime endDate,
    BigDecimal price,
    String currency) {}
