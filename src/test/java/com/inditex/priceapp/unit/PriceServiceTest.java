package com.inditex.priceapp.unit;

import com.inditex.priceapp.application.PriceService;
import com.inditex.priceapp.domain.model.Price;
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
    void selectsByPriorityThenMostRecentStartDate() {
        PriceRepositoryPort repo = mock(PriceRepositoryPort.class);
        PriceService service = new PriceService(repo);

        LocalDateTime date = LocalDateTime.parse("2020-06-14T16:00:00");

        // Candidatos: misma fecha de aplicación; diferentes priority y startDate
        Price lowPriority = new Price(1L,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                1, 35455L, 0, new BigDecimal("35.50"), "EUR");

        Price highPriorityOlder = new Price(1L,
                LocalDateTime.parse("2020-06-14T10:00:00"),
                LocalDateTime.parse("2020-06-14T17:00:00"),
                2, 35455L, 1, new BigDecimal("30.00"), "EUR");

        Price highPriorityNewer = new Price(1L,
                LocalDateTime.parse("2020-06-14T15:00:00"),
                LocalDateTime.parse("2020-06-14T18:30:00"),
                2, 35455L, 1, new BigDecimal("25.45"), "EUR");

        when(repo.findCandidates(1L, 35455L, date))
                .thenReturn(List.of(lowPriority, highPriorityOlder, highPriorityNewer));

        Optional<Price> result = service.getApplicablePrice(1L, 35455L, date);

        assertTrue(result.isPresent());
        // Debe elegir el de mayor prioridad (1) y en empate, el de startDate más reciente (15:00)
        assertEquals(2, result.get().getPriceList());
        assertEquals(LocalDateTime.parse("2020-06-14T15:00:00"), result.get().getStartDate());

        verify(repo, times(1)).findCandidates(1L, 35455L, date);
    }
}
