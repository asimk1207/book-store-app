package com.store.book.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class DiscountDTO implements Serializable {

  private static final long serialVersionUID = 851710346493167567L;

  private Long discountId;

  private String promotionCode;

  private BigDecimal discPercent;

}
