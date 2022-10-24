package com.store.book.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.store.book.dto.BookDTO;
import com.store.book.dto.BookRequestDTO;
import com.store.book.dto.BookResponseDTO;
import com.store.book.dto.ChargeDTO;
import com.store.book.model.Discount;
import com.store.book.repository.DiscountRepository;
import com.store.book.service.BookService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Slf4j
@RestController
@RequestMapping("/restapi/books")
public class BookManagementController {

  @Autowired
  private BookService bookService;

  @Autowired
  private DiscountRepository discountRepository;

  public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

  @GetMapping
  public ResponseEntity<List<BookDTO>> retrieveAllBooks() {
    List<BookDTO> allBooks = bookService.findAllBooks();
    if (CollectionUtils.isEmpty(allBooks)) {
      log.info("No Books found");
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(allBooks, HttpStatus.OK);
  }

  @GetMapping("/{bookId}")
  public EntityModel<BookDTO> retrieveBook(@PathVariable Long bookId) {
    BookDTO book = bookService.findBookById(bookId);
    EntityModel<BookDTO> entityModel = EntityModel.of(book);
    WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllBooks());
    entityModel.add(link.withRel("all-books"));
    return entityModel;
  }

  @PostMapping
  public ResponseEntity<BookDTO> saveBook(@Valid @RequestBody BookDTO bookDTO) {
    log.info("Saving new Book");
    BookDTO book = bookService.saveBook(bookDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{bookId}")
        .buildAndExpand(book.getBookId())
        .toUri();
    return ResponseEntity.created(location).build();
  }

  @PutMapping
  public ResponseEntity<BookDTO> updateBook(@Valid @RequestBody BookDTO bookDTO) {
    log.info("Updating Book");
    BookDTO book = bookService.updateBook(bookDTO);
    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  @DeleteMapping("/{bookId}")
  public ResponseEntity<Object> deleteBook(@PathVariable Long bookId) {
    bookService.deleteBook(bookId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/checkout")
  public ResponseEntity<BookResponseDTO> checkoutBooks(@RequestBody BookRequestDTO request) {

    if (CollectionUtils.isEmpty(request.getBooks())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    BookResponseDTO response = new BookResponseDTO();
    response.setBooks(new ArrayList<>());
    ChargeDTO charge = new ChargeDTO();
    charge.setQuatity(request.getBooks().size());
    charge.setBaseAmount(BigDecimal.ZERO);
    charge.setDiscAmount(BigDecimal.ZERO);
    response.setCharge(charge);

    BigDecimal discPercent = null;
    if (Objects.nonNull(request.getPromotionCode())) {
      // Discount will be applied by selected promotion code
      Discount disc = discountRepository
          .findDiscPercentByPromotionCodeIgnoreCase(request.getPromotionCode());
      discPercent = Objects.nonNull(disc) ? disc.getDiscPercent() : null;
    }

    if (Objects.nonNull(discPercent)) {
      // Apply promotional discount
      charge.setDiscType("PROMOTIONAL_DISCOUNT");
      charge.setDiscPercent(discPercent);
      final BigDecimal discPer = discPercent;
      request.getBooks().forEach(book -> {
        BookDTO selectedBook = bookService.getBookByNameAndAuthorAndIsbn(book);
        charge.setBaseAmount(charge.getBaseAmount().add(selectedBook.getPrice()));
        BigDecimal discAmount = selectedBook.getPrice().multiply(discPer).divide(ONE_HUNDRED);
        charge.setDiscAmount(charge.getDiscAmount().add(discAmount));
        response.getBooks().add(selectedBook);
      });
    } else {
      // Apply discount by category
      charge.setDiscType("CATEGORY_DISCOUNT");
      request.getBooks().forEach(book -> {
        BookDTO selectedBook = bookService.getBookByNameAndAuthorAndIsbn(book);
        charge.setBaseAmount(charge.getBaseAmount().add(selectedBook.getPrice()));
        BigDecimal discAmount = BigDecimal.ZERO;
        if (Objects.nonNull(selectedBook.getBookType().getDiscount())) {
          BigDecimal discPer = selectedBook.getBookType().getDiscount().getDiscPercent();
          discAmount = selectedBook.getPrice().multiply(discPer).divide(ONE_HUNDRED);
        }
        charge.setDiscAmount(charge.getDiscAmount().add(discAmount));
        response.getBooks().add(selectedBook);
      });
    }
    charge.setDiscAmount(charge.getDiscAmount().setScale(2, BigDecimal.ROUND_UP));
    charge.setTotalAmount(charge.getBaseAmount().subtract(charge.getDiscAmount()));
    charge.setTotalAmount(charge.getTotalAmount().setScale(2, BigDecimal.ROUND_UP));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
