package com.oku.library.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @Column(name = "bookId")
    private Long bookId;
    @Column(name = "isbn")
    private Long isbn;
    @Column(name = "title")
    private String title;
    @Column(name = "publishDate")
    private Year publishDate;
    @ManyToOne(targetEntity = Author.class)
    @JoinColumn(name = "authorId")
    private Author author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(bookId);
    }
}
