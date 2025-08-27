package com.inditex.infrastructure.controller;

import com.inditex.application.dto.PriceResponse;
import com.inditex.application.service.PriceService;
import com.inditex.infrastructure.controller.dto.PriceRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling price-related HTTP requests. Provides endpoints for retrieving
 * product price information.
 */
@RestController
@RequestMapping("/api/v1/prices")
@Tag(name = "Prices", description = "API for price management and queries")
@RequiredArgsConstructor
public class PriceController {

  private final PriceService priceService;

  /**
   * Retrieves the applicable price for a product based on specified criteria including application
   * date, product ID, and brand ID.
   *
   * <p>This endpoint searches for the most appropriate price based on business rules:
   *
   * <ul>
   *   <li>Finds prices valid for the given date-time
   *   <li>Selects the price with the highest priority
   *   <li>Returns the final price with all relevant details
   * </ul>
   *
   * <p><b>Example Usage:</b> {@code GET
   * /api/prices/applicable?date=2023-12-31T10:00:00&productId=35455&brandId=1}
   *
   * @param request the price request parameters containing:
   *     <ul>
   *       <li>{@code date} - Application date and time (required)
   *       <li>{@code productId} - Product identifier (required, positive)
   *       <li>{@code brandId} - Brand identifier (required, positive)
   *     </ul>
   *
   * @return ResponseEntity containing the applicable {@link PriceResponse} with HTTP 200 status
   * @throws com.inditex.domain.exception.PriceNotFoundException if no applicable price is found
   * @throws jakarta.validation.ConstraintViolationException if input validation fails
   * @see PriceService#getApplicablePrice(LocalDateTime, Long, Long)
   */
  @GetMapping()
  @Operation(
      summary = "Get applicable product price",
      description =
          "Retrieves the applicable price for a specific product, brand, and date combination. "
              + "The system returns the price with the highest priority that is valid "
              + "for the given datetime.",
      parameters = {
        @Parameter(
            name = "date",
            description = "Application date and time in ISO format (YYYY-MM-DDTHH:MM:SS)",
            example = "2023-12-31T10:00:00",
            required = true),
        @Parameter(
            name = "productId",
            description = "Product identifier (positive integer)",
            example = "35455",
            required = true),
        @Parameter(
            name = "brandId",
            description = "Brand identifier (positive integer)",
            example = "1",
            required = true)
      })
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Price found successfully",
        content = @Content(schema = @Schema(implementation = PriceResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Invalid input parameters",
        content = @Content(schema = @Schema(implementation = Error.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No applicable price found for the given criteria",
        content = @Content(schema = @Schema(implementation = Error.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Internal server error",
        content = @Content(schema = @Schema(implementation = Error.class)))
  })
  public ResponseEntity<PriceResponse> getApplicablePrice(
      @Parameter(hidden = true) @Valid PriceRequest request) {

    PriceResponse response =
        priceService.getApplicablePrice(
            request.getDate(), request.getProductId(), request.getBrandId());
    return ResponseEntity.ok(response);
  }
}
