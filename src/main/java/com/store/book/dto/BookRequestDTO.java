package com.store.book.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Data
public class BookRequestDTO implements Serializable {

  private static final long serialVersionUID = 6923560640325175571L;

  private List<BookDTO> books;

  private String promotionCode;

}
