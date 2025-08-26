package com.inditex.priceapp.unit;

import com.inditex.priceapp.domain.model.Price;
import com.inditex.priceapp.domain.model.policy.DefaultPriceSelectionPolicy;
import com.inditex.priceapp.domain.model.policy.PriceSelectionPolicy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceSelectionPolicyTest {

    @Test
    void comparator_ordersByPriorityThenStartDateDesc() {
        PriceSelectionPolicy policy = new DefaultPriceSelectionPolicy();
        LocalDateTime date = LocalDateTime.parse("2020-06-14T16:00:00");

        Price low = new Price(1L, date.minusHours(10), date.plusHours(10), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");
        Price highOld = new Price(1L, date.minusHours(6), date.plusHours(1), 2, 35455L, 1, new BigDecimal("30.00"), "EUR");
        Price highNew = new Price(1L, date.minusHours(1), date.plusHours(2), 2, 35455L, 1, new BigDecimal("25.45"), "EUR");

        List<Price> sorted = List.of(low, highOld, highNew).stream()
                .sorted(policy.comparator()).toList();

        assertEquals(highNew.getPriceList(), sorted.get(0).getPriceList());
    }
}
