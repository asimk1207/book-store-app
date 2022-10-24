package com.store.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.book.model.Discount;

/**
 * @author Mohammad.Asim.Khan
 *
 */
public interface DiscountRepository extends JpaRepository<Discount, Long> {

  /**
   * Find Discount Percentage by promotionCode ignore case.
   *
   * @param promotionCode
   * @return
   */
  Discount findDiscPercentByPromotionCodeIgnoreCase(String promotionCode);

}
