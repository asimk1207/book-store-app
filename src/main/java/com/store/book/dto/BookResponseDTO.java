package com.store.book.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Data
public class BookResponseDTO implements Serializable {

  private static final long serialVersionUID = -8705355101954218916L;

  private List<BookDTO> books;

  private ChargeDTO charge;

}
