package com.oku.library.jpa.repo;

import com.oku.library.controller.dto.BookAuthorInventoryDto;
import com.oku.library.controller.dto.IsbnOnly;
import com.oku.library.jpa.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Year;
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

    List<IsbnOnly> findAllBy();

    @Query("SELECT new com.oku.library.controller.dto.BookAuthorInventoryDto(b.isbn, b.title, b.publishDate, a.authorName, a.authorSurname, i.price, i.stock)" +
            " from Inventory i JOIN Book b ON i.isbn = b.isbn JOIN Author a on a.authorId = b.author.authorId WHERE b.title = :title")
    Optional<BookAuthorInventoryDto> findBookAuthorInventory(@Param("title") String title);

    @Query("SELECT b FROM Book b where b.publishDate BETWEEN :publishDate1 AND :publishDate2")
    Page<Book> findAllByPublishDate(Pageable pageable ,@Param("publishDate1") Year initialDate,@Param("publishDate2") Year endDate);
}
