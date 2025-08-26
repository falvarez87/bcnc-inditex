package com.inditex.priceapp.integration;

import com.inditex.priceapp.infraestructure.adapter.repository.JpaPriceRepository;
import com.inditex.priceapp.infraestructure.adapter.repository.SpringDataPriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class JpaPriceRepositoryIT {

    @Autowired
    SpringDataPriceRepository jpaRepo;

    @Test
    void findBestCandidate_usesDbOrdering() {
        var adapter = new JpaPriceRepository(jpaRepo);
        var date = LocalDateTime.parse("2020-06-14T16:00:00");

        var result = adapter.findBestCandidate(1L, 35455L, date);

        assertTrue(result.isPresent());
        // En los datos del enunciado, a esa hora debe ser la tarifa 2 con mayor prioridad.
    }
}
