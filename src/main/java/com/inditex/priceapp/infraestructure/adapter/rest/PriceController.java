package com.inditex.priceapp.infraestructure.adapter.rest;

import com.inditex.priceapp.application.PriceService;
import com.inditex.priceapp.domain.model.Price;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PriceController {

    private final PriceService service;

    public PriceController(PriceService service) {
        this.service = service;
    }

    // Nuevo path RESTful
    @GetMapping("/brands/{brandId}/products/{productId}/price")
    public ResponseEntity<?> getPriceRestful(
            @PathVariable Long brandId,
            @PathVariable Long productId,
            @RequestParam(name = "date")
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return mapToResponse(service.getApplicablePrice(brandId, productId, date));
    }

    // Mantener compatibilidad con el endpoint anterior (para tus tests actuales)
    @GetMapping("/prices")
    public ResponseEntity<?> getPriceLegacy(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam(name = "productId") @NotNull Long productId,
            @RequestParam(name = "brandId") @NotNull Long brandId) {
        return mapToResponse(service.getApplicablePrice(brandId, productId, date));
    }

    private ResponseEntity<?> mapToResponse(Optional<Price> price) {
        return price.<ResponseEntity<?>>map(p -> ResponseEntity.ok(
                        new PriceResponse(
                                p.getProductId(),
                                p.getBrandId(),
                                p.getPriceList(),
                                p.getStartDate(),
                                p.getEndDate(),
                                p.getPrice(),
                                p.getCurrency()
                        )
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
