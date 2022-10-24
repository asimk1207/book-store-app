package com.store.book.service;

import java.util.List;

import com.store.book.dto.BookDTO;

/**
 * @author Mohammad.Asim.Khan
 *
 */
public interface BookService {

  /**
   * Find all books.
   *
   * @return
   */
  List<BookDTO> findAllBooks();

  /**
   * Find book by id.
   *
   * @param bookId
   * @return
   */
  BookDTO findBookById(Long bookId);

  /**
   * Gets the book by name.
   *
   * @param bookName
   * @return
   */
  BookDTO getBookByName(String bookName);
  
  /**
   * Find Book by Name, Author and ISBN.
   *
   * @param book
   * @return
   */
  BookDTO getBookByNameAndAuthorAndIsbn(BookDTO book);

  /**
   * Update book.
   *
   * @param bookDTO
   * @return
   */
  BookDTO updateBook(BookDTO bookDTO);

  /**
   * Delete book.
   *
   * @param bookId
   */
  void deleteBook(Long bookId);

  /**
   * Save book.
   *
   * @param bookDTO
   * @return
   */
  BookDTO saveBook(BookDTO bookDTO);

}
