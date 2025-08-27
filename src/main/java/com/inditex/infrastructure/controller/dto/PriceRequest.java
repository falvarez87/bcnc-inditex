package com.inditex.infrastructure.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for price request parameters. Represents the input criteria for searching
 * applicable prices.
 *
 * <p>This class is used as a parameter object for the price lookup endpoint and includes validation
 * constraints to ensure data integrity.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request parameters for price lookup operation")
public class PriceRequest {

  /**
   * The date and time for which the price applicability is being checked. Must be provided in
   * ISO-8601 format (YYYY-MM-DDTHH:MM:SS).
   */
  @NotNull(message = "Application date is required")
  @Schema(
      description = "Application date and time in ISO format",
      example = "2023-12-31T10:00:00",
      required = true)
  private LocalDateTime date;

  /**
   * Unique identifier of the product for which the price is being requested. Must be a positive
   * integer value.
   */
  @NotNull(message = "Product ID is required")
  @Positive(message = "Product ID must be a positive number")
  @Schema(description = "Product identifier", example = "35455", required = true)
  private Long productId;

  /**
   * Unique identifier of the brand associated with the product. Must be a positive integer value.
   */
  @NotNull(message = "Brand ID is required")
  @Positive(message = "Brand ID must be a positive number")
  @Schema(description = "Brand identifier", example = "1", required = true)
  private Long brandId;
}
