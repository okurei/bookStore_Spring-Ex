package com.oku.library.jpa.repo;

import com.oku.library.jpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    // TODO implement this query with the DTO     @Query("select b.isbn, b.title, b.publishDate from Book b where b.isbn = :isbn")
    @Query("select b from Book b where b.isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn") Long isbn);
}
