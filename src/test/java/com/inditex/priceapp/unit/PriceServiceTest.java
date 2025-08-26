package com.inditex.priceapp.unit;

import com.inditex.priceapp.application.PriceService;
import com.inditex.priceapp.domain.model.Price;
import com.inditex.priceapp.domain.model.policy.DefaultPriceSelectionPolicy;
import com.inditex.priceapp.domain.model.policy.PriceSelectionPolicy;
import com.inditex.priceapp.domain.port.PriceRepositoryPort;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    @Test
    void usesRepositoryBestCandidateIfPresent() {
        PriceRepositoryPort repo = mock(PriceRepositoryPort.class);
        PriceSelectionPolicy policy = new DefaultPriceSelectionPolicy();
        PriceService service = new PriceService(repo, policy);

        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00");
        Price price = new Price(1L, date.minusHours(1), date.plusHours(1), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");

        when(repo.findBestCandidate(1L, 35455L, date)).thenReturn(Optional.of(price));

        var result = service.getApplicablePrice(1L, 35455L, date);

        assertTrue(result.isPresent());
        verify(repo).findBestCandidate(1L, 35455L, date);
        verify(repo, never()).findCandidates(any(), any(), any());
    }

    @Test
    void fallsBackToPolicyWhenBestCandidateEmpty() {
        PriceRepositoryPort repo = mock(PriceRepositoryPort.class);
        PriceSelectionPolicy policy = new DefaultPriceSelectionPolicy();
        PriceService service = new PriceService(repo, policy);

        LocalDateTime date = LocalDateTime.parse("2020-06-14T16:00:00");
        Price low = new Price(1L, date.minusHours(10), date.plusHours(10), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");
        Price highNew = new Price(1L, date.minusHours(1), date.plusHours(2), 2, 35455L, 1, new BigDecimal("25.45"), "EUR");

        when(repo.findBestCandidate(1L, 35455L, date)).thenReturn(Optional.empty());
        when(repo.findCandidates(1L, 35455L, date)).thenReturn(List.of(low, highNew));

        var result = service.getApplicablePrice(1L, 35455L, date);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().getPriceList());
        verify(repo).findCandidates(1L, 35455L, date);
    }
}
