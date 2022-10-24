package com.store.book.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Data
@AllArgsConstructor
public class ErrorDetails {
  
  private LocalDateTime timestamp;
  private String message;
  private String details;

}
