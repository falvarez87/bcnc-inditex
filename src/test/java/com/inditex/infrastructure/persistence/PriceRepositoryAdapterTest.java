package com.inditex.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.inditex.domain.model.Price;
import com.inditex.infrastructure.persistence.entity.PriceEntity;
import com.inditex.infrastructure.persistence.mapper.PriceMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryAdapterTest {

  @Mock private JpaPriceRepository jpaPriceRepository;

  @Mock private PriceMapper priceMapper;

  @InjectMocks private PriceRepositoryAdapter priceRepositoryAdapter;

  @Test
  void findApplicablePrice_WithValidParams_ReturnsPrice() {
    // Given
    Long brandId = 1L;
    Long productId = 35455L;
    LocalDateTime date = LocalDateTime.now();

    PriceEntity entity = new PriceEntity();
    Price domain = Price.builder().build();

    when(jpaPriceRepository.findApplicablePrice(any(), any(), any()))
        .thenReturn(Optional.of(entity));
    when(priceMapper.toDomain(entity)).thenReturn(domain);

    // When
    Optional<Price> result = priceRepositoryAdapter.findApplicablePrice(date, productId, brandId);

    // Then
    assertTrue(result.isPresent());
    verify(jpaPriceRepository).findApplicablePrice(date, productId, brandId);
    verify(priceMapper).toDomain(entity);
  }

  @Test
  void findApplicablePrice_WithNoResult_ReturnsEmpty() {
    // Given
    when(jpaPriceRepository.findApplicablePrice(any(), any(), any())).thenReturn(Optional.empty());

    // When
    Optional<Price> result =
        priceRepositoryAdapter.findApplicablePrice(LocalDateTime.now(), 35455L, 1L);

    // Then
    assertFalse(result.isPresent());
    verify(priceMapper, never()).toDomain(any());
  }
}
