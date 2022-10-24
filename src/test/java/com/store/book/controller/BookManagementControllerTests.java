package com.store.book.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.book.dto.BookDTO;
import com.store.book.dto.BookRequestDTO;
import com.store.book.dto.BookTypeDTO;
import com.store.book.dto.DiscountDTO;
import com.store.book.model.Discount;
import com.store.book.repository.DiscountRepository;
import com.store.book.service.BookService;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@WebMvcTest(BookManagementController.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BookManagementControllerTests {

  private static final String RESTAPI_BOOKS_URI = "/restapi/books/";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  @MockBean
  private DiscountRepository discountRepository;

  @Autowired
  private ObjectMapper mapper;

  private BookDTO book1;
  private List<BookDTO> books;

  @BeforeEach
  void setUp() throws Exception {
    book1 = new BookDTO("First Book", "First Book Description", "AuthorName1", "ISBN-1",
        new BigDecimal(100));
    book1.setBookId(1l);
    BookDTO book2 = new BookDTO("Second Book", "Second Book Description", "AuthorName2", "ISBN-2",
        new BigDecimal(150));
    book2.setBookId(2l);
    BookDTO book3 = new BookDTO("Third Book", "Third Book Description", "AuthorName3", "ISBN-3",
        new BigDecimal(200));
    book3.setBookId(3l);

    books = Arrays.asList(book1, book2, book3);
  }

  @Test
  void testRetrieveAllBooks() throws Exception {
    Mockito.when(bookService.findAllBooks()).thenReturn(books);
    mockMvc
        .perform(
            MockMvcRequestBuilders.get(RESTAPI_BOOKS_URI).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void testRetrieveAllBooks_IsEmpty() throws Exception {
    Mockito.when(bookService.findAllBooks()).thenReturn(null);
    mockMvc
        .perform(
            MockMvcRequestBuilders.get(RESTAPI_BOOKS_URI).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void testRetrieveBook() throws Exception {
    Mockito.when(bookService.findBookById(book1.getBookId())).thenReturn(book1);
    mockMvc
        .perform(MockMvcRequestBuilders.get(RESTAPI_BOOKS_URI + "/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", notNullValue()))
        .andExpect(jsonPath("$.name", is("First Book")));
  }

  @Test
  void testSaveBook_Success() throws Exception {
    BookDTO book = new BookDTO("New Book", "New Book Description", "AuthorName4", "ISBN-4",
        new BigDecimal(200));
    Mockito.when(bookService.saveBook(book)).thenReturn(book);
    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(RESTAPI_BOOKS_URI)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(book));
    mockMvc.perform(mockRequest).andExpect(status().isCreated());
  }

  @Test
  void testUpdateBook_Success() throws Exception {
    Mockito.when(bookService.findBookById(book1.getBookId())).thenReturn(book1);
    Mockito.when(bookService.updateBook(book1)).thenReturn(book1);
    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put(RESTAPI_BOOKS_URI)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(book1));
    mockMvc.perform(mockRequest).andExpect(status().isOk());
  }

  @Test
  void testDeleteBook_Success() throws Exception {
    Mockito.when(bookService.findBookById(1l)).thenReturn(book1);
    mockMvc.perform(MockMvcRequestBuilders.delete(RESTAPI_BOOKS_URI + "/1")
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
  }

  @Test
  void testCheckoutBooks_PomotionDiscount() throws Exception {
    BookRequestDTO request = new BookRequestDTO();
    List<BookDTO> books = Arrays.asList(book1);
    request.setBooks(books);
    request.setPromotionCode("SAVE-10");

    Discount disc = new Discount();
    disc.setDiscPercent(new BigDecimal(10));
    Mockito.when(discountRepository.findDiscPercentByPromotionCodeIgnoreCase(Mockito.anyString()))
        .thenReturn(disc);

    Mockito.when(bookService.getBookByNameAndAuthorAndIsbn(Mockito.any())).thenReturn(book1);

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
        .post(RESTAPI_BOOKS_URI + "/checkout")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(request));

    mockMvc.perform(mockRequest).andExpect(status().isOk());
  }

  @Test
  void testCheckoutBooks_CategoryDiscount() throws Exception {
    DiscountDTO disc = new DiscountDTO();
    disc.setDiscPercent(new BigDecimal(10));
    BookTypeDTO bookType = new BookTypeDTO();
    bookType.setDiscount(disc);
    book1.setBookType(bookType);

    BookRequestDTO request = new BookRequestDTO();
    List<BookDTO> books = Arrays.asList(book1);
    request.setBooks(books);

    Mockito.when(bookService.getBookByNameAndAuthorAndIsbn(Mockito.any())).thenReturn(book1);

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
        .post(RESTAPI_BOOKS_URI + "/checkout")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(request));

    mockMvc.perform(mockRequest).andExpect(status().isOk());
  }

}
