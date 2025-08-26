package com.inditex.priceapp.domain.model.policy;

import com.inditex.priceapp.domain.model.Price;

import java.util.Comparator;

public class DefaultPriceSelectionPolicy implements PriceSelectionPolicy {
    @Override
    public Comparator<Price> comparator() {
        return Comparator
                .comparingInt(Price::getPriority).reversed()
                .thenComparing(Price::getStartDate, Comparator.reverseOrder());
    }
}
