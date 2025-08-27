package com.inditex.application.service;

import com.inditex.domain.model.Price;
import com.inditex.domain.repository.PriceRepository;
import com.inditex.application.dto.PriceResponse;
import com.inditex.domain.exception.PriceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public PriceResponse getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository.findApplicablePrice(applicationDate, productId, brandId)
                .map(this::convertToResponse)
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("No price found for date: %s, productId: %d, brandId: %d",
                                applicationDate, productId, brandId)
                ));
    }

    private PriceResponse convertToResponse(Price price) {
        return new PriceResponse(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice(),
                price.getCurrency()
        );
    }
}