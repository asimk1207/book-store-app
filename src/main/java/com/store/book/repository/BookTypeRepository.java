package com.store.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.book.model.BookType;

/**
 * @author Mohammad.Asim.Khan
 *
 */
public interface BookTypeRepository extends JpaRepository<BookType, Long> {

}
