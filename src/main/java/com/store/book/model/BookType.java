package com.store.book.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
public class BookType implements Serializable {

  private static final long serialVersionUID = 7638823779951814829L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long bookTypeId;

  @NonNull
  private String typeCode;

  @NonNull
  private String typeName;

  @OneToMany(mappedBy = "bookType")
  private Set<Book> books;

  @OneToOne
  @JoinColumn(name = "DISCOUNT_ID")
  private Discount discount;

}
