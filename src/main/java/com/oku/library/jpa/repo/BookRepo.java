package com.oku.library.jpa.repo;

import com.oku.library.jpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn") Long isbn);

    @Query("select b from Book b where b.title = :title")
    Optional<Book> findByTitle(@Param("title") String title);

    @Query("select b from Book b where b.author.authorSurname = :authorName")
    List<Book> findAllBookOfAuthor(@Param("authorName") String authorName);

    @Query("select b from Book b where b.isbn = :isbn")
    Book getBookByIsbn(Long isbn);
}
