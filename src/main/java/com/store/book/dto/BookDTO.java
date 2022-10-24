package com.store.book.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class BookDTO implements Serializable {

  private static final long serialVersionUID = -9060823831819197926L;

  private Long bookId;

  @NonNull
  @NotBlank(message = "Book Name is mandatory")
  private String name;

  @NonNull
  private String description;

  @NonNull
  @NotBlank(message = "Author Name is mandatory")
  private String author;

  @NonNull
  @NotBlank(message = "ISBN is mandatory")
  private String isbn;

  @NonNull
  @NotNull(message = "Price is mandatory")
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 10, fraction = 2)
  private BigDecimal price;

  private BookTypeDTO bookType;

}
