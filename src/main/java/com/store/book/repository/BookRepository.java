package com.store.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.book.model.Book;

/**
 * @author Mohammad.Asim.Khan
 *
 */
public interface BookRepository extends JpaRepository<Book, Long> {

  /**
   * Find Book by name ignore case.
   *
   * @param bookName
   * @return
   */
  Book findByNameIgnoreCase(String bookName);
  
  /**
   * Find Book by Name, Author and ISBN.
   *
   * @param name
   * @param author
   * @param isbn
   * @return
   */
  Book findByNameAndAuthorAndIsbn(String name, String author, String isbn);

}
