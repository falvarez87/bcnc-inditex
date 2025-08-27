package com.inditex.infrastructure.controller;

import com.inditex.application.service.PriceService;
import com.inditex.application.dto.PriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@Tag(name = "Prices", description = "API for price management and queries")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    @Operation(
            summary = "Get applicable price",
            description = "Returns the applicable price for a product in a brand at a specific date and time"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Price found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponse.class),
                            examples = @ExampleObject(
                                    value = "{\"productId\": 35455, \"brandId\": 1, \"priceList\": 1, \"startDate\": \"2020-06-14T00:00:00\", \"endDate\": \"2020-12-31T23:59:59\", \"price\": 35.50, \"currency\": \"EUR\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Price not found for the given parameters"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input parameters"
            )
    })
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @Parameter(
                    description = "Application date and time in ISO format (YYYY-MM-DDTHH:MM:SS)",
                    example = "2020-06-14T10:00:00",
                    required = true
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,

            @Parameter(
                    description = "Product identifier",
                    example = "35455",
                    required = true
            )
            @RequestParam @NotNull Long productId,

            @Parameter(
                    description = "Brand identifier (1 = ZARA)",
                    example = "1",
                    required = true
            )
            @RequestParam @NotNull Long brandId) {

        PriceResponse response = priceService.getApplicablePrice(date, productId, brandId);
        return ResponseEntity.ok(response);
    }
}