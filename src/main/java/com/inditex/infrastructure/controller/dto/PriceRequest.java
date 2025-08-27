package com.inditex.infrastructure.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PriceRequest(
        @NotNull
        LocalDateTime applicationDate,

        @NotNull
        Long productId,

        @NotNull
        Long brandId
) {}
