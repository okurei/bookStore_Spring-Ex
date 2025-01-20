package com.oku.library.controller.dto;


import com.oku.library.jpa.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class BookDto {
    private Long bookId;
    private Long isbn;
    private String title;
    private Year publishDate;
    private Long authorId;

    public BookDto(Book book){
        this.isbn = book.getIsbn();
        this.publishDate= book.getPublishDate();
        this.title = book.getTitle();
        this.bookId = book.getBookId();
        this.authorId = book.getAuthor().getAuthorId();
    }

    public static BookDto bookToDto(Book book){
        return new BookDto(book);
    }
}
