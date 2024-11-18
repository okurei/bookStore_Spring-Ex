package com.oku.library.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Year;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
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

}
