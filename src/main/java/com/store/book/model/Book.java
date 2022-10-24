package com.store.book.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohammad.Asim.Khan
 *
 */
@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Book implements Serializable {

  private static final long serialVersionUID = -4347645642779681278L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long bookId;

  @NonNull
  private String name;

  @NonNull
  private String description;

  @NonNull
  private String author;

  @NonNull
  private String isbn;

  @NonNull
  @Column(precision = 10, scale = 2)
  private BigDecimal price;

  @NonNull
  @ManyToOne
  @JoinColumn(name = "BOOK_TYPE_ID")
  private BookType bookType;

}
