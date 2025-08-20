package com.inditex.priceapp.infraestructure.adapter.rest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceResponse {
    public Long productId;
    public Long brandId;
    public Integer priceList;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public BigDecimal price;
    public String currency;

    public PriceResponse(Long productId, Long brandId, Integer priceList, LocalDateTime startDate,
                         LocalDateTime endDate, BigDecimal price, String currency) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.currency = currency;
    }
}
