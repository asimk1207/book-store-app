package com.store.book.config;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store.book.model.Book;
import com.store.book.model.BookType;
import com.store.book.model.Discount;
import com.store.book.repository.BookRepository;
import com.store.book.repository.BookTypeRepository;
import com.store.book.repository.DiscountRepository;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Configuration
public class LoadDatabase {

  @Autowired
  private BookRepository bookRepository;
  
  @Autowired
  private BookTypeRepository bookTypeRepository;
  
  @Autowired
  private DiscountRepository discountRepository;

  @Bean
  public CommandLineRunner initDatabase() {
    return args -> {
      Discount discount1 = new Discount("SAVE-5", new BigDecimal(5));
      Discount discount2 = new Discount("SAVE-10", new BigDecimal(10));
      Discount discount3 = new Discount("SAVE-15", new BigDecimal(15));
      discountRepository.saveAll(Arrays.asList(discount1, discount2, discount3));

      BookType bookType1 = new BookType("FICTION", "Fiction");
      bookType1.setDiscount(discount1);
      BookType bookType2 = new BookType("COMIC", "Comic");
      bookType2.setDiscount(discount2);
      BookType bookType3 = new BookType("DRAMA", "Drama");
      bookType3.setDiscount(discount1);
      BookType bookType4 = new BookType("POETRY", "Poetry");
      bookType4.setDiscount(discount3);
      bookTypeRepository.saveAll(Arrays.asList(bookType1, bookType2, bookType3, bookType4));

      Book book1 = new Book("First Book", "First Book Description", "AuthorName1", "ISBN-1",
          new BigDecimal(100), bookType1);
      Book book2 = new Book("Second Book", "Second Book Description", "AuthorName2", "ISBN-2",
          new BigDecimal(150), bookType2);
      Book book3 = new Book("Third Book", "Third Book Description", "AuthorName3", "ISBN-3",
          new BigDecimal(200), bookType3);
      Book book4 = new Book("Fourth Book", "Fourth Book Description", "AuthorName4", "ISBN-4",
          new BigDecimal(250), bookType4);
      Book book5 = new Book("Fifth Book", "Fifth Book Description", "AuthorName5", "ISBN-5",
          new BigDecimal(300), bookType1);
      Book book6 = new Book("Sixth Book", "Sixth Book Description", "AuthorName6", "ISBN-6",
          new BigDecimal(350), bookType2);
      bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6));

      System.out.println("Database initialized for Book-Store Application");
    };
  }

}
