package com.store.book.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.book.dto.BookDTO;
import com.store.book.exception.ResourceNotFoundException;
import com.store.book.model.Book;
import com.store.book.repository.BookRepository;
import com.store.book.service.BookService;
import com.store.book.util.ApplicationUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {

  @Autowired
  private BookRepository bookRepository;

  @Override
  public List<BookDTO> findAllBooks() {
    log.info("Fetching all books");
    List<Book> books = bookRepository.findAll();
    return ApplicationUtils.mapAll(books, BookDTO.class);
  }

  @Override
  public BookDTO findBookById(Long bookId) {
    log.info("Fetching Book [id = {}]", bookId);
    Optional<Book> bookOptional = bookRepository.findById(bookId);
    if (bookOptional.isPresent()) {
      return ApplicationUtils.map(bookOptional.get(), BookDTO.class);
    } else {
      throw new ResourceNotFoundException("Book [id = {" + bookId + "}] not found");
    }
  }

  @Override
  public BookDTO getBookByName(String bookName) {
    log.info("Fetching Book [Name = {}]", bookName);
    Book book = bookRepository.findByNameIgnoreCase(bookName);
    if (book != null) {
      return ApplicationUtils.map(book, BookDTO.class);
    } else {
      throw new ResourceNotFoundException("Book [name = {" + bookName + "}] not found");
    }
  }

  @Override
  public BookDTO getBookByNameAndAuthorAndIsbn(BookDTO bookDTO) {
    log.info("Fetching Book [Name = {}, Author = {}, ISBN = {}]", bookDTO.getName(),
        bookDTO.getAuthor(), bookDTO.getIsbn());
    Book book = bookRepository.findByNameAndAuthorAndIsbn(bookDTO.getName(), bookDTO.getAuthor(),
        bookDTO.getIsbn());
    if (book != null) {
      return ApplicationUtils.map(book, BookDTO.class);
    } else {
      throw new ResourceNotFoundException("Book [name = {" + bookDTO.getName() + "}] not found");
    }
  }

  @Override
  public BookDTO updateBook(BookDTO bookDTO) {
    log.info("Updating Book [id = {}]", bookDTO.getBookId());
    Optional<Book> bookOptional = bookRepository.findById(bookDTO.getBookId());
    if (bookOptional.isPresent()) {
      Book book = ApplicationUtils.map(bookDTO, bookOptional.get());
      book = bookRepository.save(book);
      log.info("Book [id = {}] updated successfully", bookDTO.getBookId());
      return ApplicationUtils.map(book, BookDTO.class);
    } else {
      throw new ResourceNotFoundException("Book [id = {" + bookDTO.getBookId() + "}] not found");
    }
  }

  @Override
  public void deleteBook(Long bookId) {
    log.info("Deleting Book [id = {}]", bookId);
    Optional<Book> bookOpt = bookRepository.findById(bookId);
    if (bookOpt.isPresent()) {
      bookRepository.delete(bookOpt.get());
      log.info("Book [id = {}] deleted successfully", bookId);
    } else {
      throw new ResourceNotFoundException("Book [id = {" + bookId + "}] not found");
    }
  }

  @Override
  public BookDTO saveBook(BookDTO bookDTO) {
    log.info("Saving Book [{}]", bookDTO);
    Book book = ApplicationUtils.map(bookDTO, Book.class);
    book = bookRepository.save(book);
    return ApplicationUtils.map(book, BookDTO.class);
  }

}
