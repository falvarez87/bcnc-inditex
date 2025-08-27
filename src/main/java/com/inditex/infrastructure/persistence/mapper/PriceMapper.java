package com.inditex.infrastructure.persistence.mapper;

import com.inditex.domain.model.Price;
import com.inditex.infrastructure.persistence.entity.PriceEntity;
import org.springframework.stereotype.Component;

/**
 * Represents a Mapper for Price (Entity <-> Domain).
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
@Component
public class PriceMapper {

  /** Handler mapper Entity to Domain. */
  public Price toDomain(PriceEntity entity) {
    return new Price(
        entity.getId(),
        entity.getBrandId(),
        entity.getProductId(),
        entity.getStartDate(),
        entity.getEndDate(),
        entity.getPriceList(),
        entity.getPriority(),
        entity.getPrice(),
        entity.getCurrency());
  }

  /** Handler mapper Domain to Entity. */
  public PriceEntity toEntity(Price domain) {
    PriceEntity entity = new PriceEntity();
    entity.setId(domain.getId());
    entity.setBrandId(domain.getBrandId());
    entity.setStartDate(domain.getStartDate());
    entity.setEndDate(domain.getEndDate());
    entity.setPriceList(domain.getPriceList());
    entity.setProductId(domain.getProductId());
    entity.setPriority(domain.getPriority());
    entity.setPrice(domain.getPrice());
    entity.setCurrency(domain.getCurrency());
    return entity;
  }
}
