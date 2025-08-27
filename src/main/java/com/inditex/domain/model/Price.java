package com.inditex.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Price {
    private Long id;
    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Long productId;
    private Integer priority;
    private BigDecimal price;
    private String currency;

    // Constructor completo
    public Price(Long id, Long brandId, LocalDateTime startDate, LocalDateTime endDate,
                 Integer priceList, Long productId, Integer priority, BigDecimal price, String currency) {
        this.id = id;
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.priority = priority;
        this.price = price;
        this.currency = currency;
    }

    // Getters
    public Long getId() { return id; }
    public Long getBrandId() { return brandId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Integer getPriceList() { return priceList; }
    public Long getProductId() { return productId; }
    public Integer getPriority() { return priority; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(id, price1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
