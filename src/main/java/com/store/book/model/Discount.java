package com.store.book.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Discount implements Serializable {

  private static final long serialVersionUID = 3833503933136270536L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long discountId;

  @NonNull
  private String promotionCode;

  @NonNull
  private BigDecimal discPercent;

}
