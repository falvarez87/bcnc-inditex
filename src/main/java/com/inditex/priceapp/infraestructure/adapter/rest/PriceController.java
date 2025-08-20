package com.inditex.priceapp.infraestructure.adapter.rest;

import com.inditex.priceapp.application.PriceService;
import com.inditex.priceapp.domain.model.Price;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceService service;

    public PriceController(PriceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam(name = "productId") @NotNull Long productId,
            @RequestParam(name = "brandId") @NotNull Long brandId) {

        Optional<Price> price = service.getApplicablePrice(brandId, productId, date);
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
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("No price found"));
    }
}
