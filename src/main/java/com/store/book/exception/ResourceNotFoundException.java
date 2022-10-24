package com.store.book.exception;

/**
 * @author Mohammad.Asim.Khan
 *
 */
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -8647889839867673714L;

  public ResourceNotFoundException(String message) {
    super(message);
  }

}
