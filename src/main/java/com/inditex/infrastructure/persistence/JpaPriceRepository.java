package com.inditex.infrastructure.persistence;

import com.inditex.infrastructure.persistence.entity.PriceEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Represents a JpaRepository for Price entity, with the used query for find applicable price.
 *
 * @author falvarez87
 * @version 1.0
 * @since 2025
 */
@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Long> {

  @Query(
      "SELECT p FROM PriceEntity p "
          + "WHERE p.brandId = :brandId "
          + "AND p.productId = :productId "
          + "AND :applicationDate BETWEEN p.startDate AND p.endDate "
          + "ORDER BY p.priority DESC "
          + "LIMIT 1")
  Optional<PriceEntity> findApplicablePrice(
      @Param("applicationDate") LocalDateTime applicationDate,
      @Param("productId") Long productId,
      @Param("brandId") Long brandId);
}
