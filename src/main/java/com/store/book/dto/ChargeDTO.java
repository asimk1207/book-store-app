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
public class ChargeDTO implements Serializable {

  private static final long serialVersionUID = 3156734773502984458L;

  private Integer quatity;

  private BigDecimal baseAmount;

  private String promotionCode;

  private String discType;
  
  private BigDecimal discPercent;
  
  private BigDecimal discAmount;

  private BigDecimal totalAmount;

}
