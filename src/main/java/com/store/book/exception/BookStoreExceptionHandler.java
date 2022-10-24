package com.store.book.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@RestControllerAdvice
public class BookStoreExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle All Exceptions
   * 
   * @param exception
   * @param request
   * @return
   * @throws Exception
   */
  @ExceptionHandler({Exception.class, RuntimeException.class})
  public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception exception,
      WebRequest request) throws Exception {
    exception.printStackTrace();
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Resource Not Found
   *
   * @param exception
   * @param request
   * @return
   * @throws Exception
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(Exception exception,
      WebRequest request) throws Exception {
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
  }

  /**
   * Validation Exceptions
   * 
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Total Errors:"
        + ex.getErrorCount() + " First Error:" + ex.getFieldError().getDefaultMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

}
