package com.inditex.priceapp.domain.model.policy;

import com.inditex.priceapp.domain.model.Price;

import java.util.Comparator;

public interface PriceSelectionPolicy {

    /**
     * Orden natural deseado para seleccionar el "ganador":
     * 1) priority DESC
     * 2) startDate DESC
     */
    Comparator<Price> comparator();
}