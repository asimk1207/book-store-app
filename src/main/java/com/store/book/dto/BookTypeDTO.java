package com.store.book.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(Include.NON_NULL)
public class BookTypeDTO implements Serializable {

  private static final long serialVersionUID = -6714756157528737089L;

  private Long bookTypeId;

  @NonNull
  private String typeCode;

  @NonNull
  private String typeName;

  private DiscountDTO discount;

}
