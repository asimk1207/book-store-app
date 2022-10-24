package com.store.book.service;

import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.store.book.dto.BookDTO;
import com.store.book.dto.BookTypeDTO;
import com.store.book.model.Book;
import com.store.book.model.BookType;
import com.store.book.repository.BookRepository;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookServiceTests {

  @Autowired
  private BookService bookService;

  @MockBean
  private BookRepository bookRepository;

  @Test
  void testFindAllBooks() {
    BookType bookType1 = new BookType("FICTION", "Fiction");
    BookType bookType2 = new BookType("COMIC", "Comic");
    Book book1 = new Book("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100), bookType1);
    Book book2 = new Book("Second Book", "Second Book Description", "AuthorName2", "ISBN-2",
        new BigDecimal(150), bookType2);
    List<Book> books = new ArrayList<Book>(Arrays.asList(book1, book2));

    doReturn(books).when(bookRepository).findAll();
    List<BookDTO> savedBooks = bookService.findAllBooks();

    Assertions.assertNotNull(savedBooks, "Books should not be NULL");
    Assertions.assertTrue(!savedBooks.isEmpty(), "Books should not be Empty");
    Assertions.assertTrue(savedBooks.size() == 2, "Books size not correct");
  }

  @Test
  void testFindBookById() {
    Book book = new Book("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100), new BookType("FICTION", "Fiction"));
    Optional<Book> bookOpt = Optional.of(book);

    doReturn(bookOpt).when(bookRepository).findById(Mockito.anyLong());
    BookDTO bookDTO = bookService.findBookById(Mockito.anyLong());

    Assertions.assertNotNull(bookDTO, "Book should not be NULL");
    Assertions.assertTrue(bookDTO.getName().equals("First Book"), "Book name should match");
  }

  @Test
  void testGetBookByName() {
    Book book = new Book("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100), new BookType("FICTION", "Fiction"));

    doReturn(book).when(bookRepository).findByNameIgnoreCase(Mockito.anyString());
    BookDTO bookDTO = bookService.getBookByName(Mockito.anyString());

    Assertions.assertNotNull(bookDTO, "Book should not be NULL");
    Assertions.assertTrue(bookDTO.getName().equals("First Book"), "Book name should match");
  }

  @Test
  void testGetBookByNameAndAuthorAndIsbn() {
    Book book = new Book("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100), new BookType("FICTION", "Fiction"));

    BookDTO bookDTO1 = new BookDTO("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100));
    doReturn(book).when(bookRepository)
        .findByNameAndAuthorAndIsbn(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    BookDTO bookDTO = bookService.getBookByNameAndAuthorAndIsbn(bookDTO1);

    Assertions.assertNotNull(bookDTO, "Book should not be NULL");
    Assertions.assertTrue(bookDTO.getName().equals("First Book"), "Book name should match");
  }

  @Test
  void testSaveBook() {
    Book book = new Book("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100), new BookType("FICTION", "Fiction"));
    doReturn(book).when(bookRepository).save(Mockito.any());

    BookDTO bookDTO = new BookDTO("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100));
    BookDTO returnBook = bookService.saveBook(bookDTO);

    Assertions.assertNotNull(returnBook, "Book should not be NULL");
  }

  @Test
  void testUpdateBook() {
    Book book = new Book("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100), new BookType("FICTION", "Fiction"));
    book.setBookId(1l);
    Optional<Book> bookOpt = Optional.of(book);

    BookDTO bookDTO = new BookDTO("First Book Updated", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100));
    BookTypeDTO bookTypeDTO = new BookTypeDTO("FICTION", "Fiction");
    bookDTO.setBookType(bookTypeDTO);
    bookDTO.setBookId(1l);
    
    doReturn(bookOpt).when(bookRepository).findById(Mockito.anyLong());
    doReturn(book).when(bookRepository).save(book);
    BookDTO returnBook = bookService.updateBook(bookDTO);

    Assertions.assertNotNull(returnBook, "Book should not be NULL");
  }

  @Test
  void testDeleteBook() {
    Book book = new Book("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100), new BookType("FICTION", "Fiction"));
    Optional<Book> bookOpt = Optional.of(book);
    doReturn(bookOpt).when(bookRepository).findById(Mockito.anyLong());
    bookService.deleteBook(Mockito.anyLong());
  }

}
