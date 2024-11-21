package com.oku.library.controller.dto;


import com.oku.library.jpa.entity.Author;
import com.oku.library.jpa.entity.Book;
import com.oku.library.jpa.entity.Inventory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.Year;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class BookAuthorInventoryDto {
    @NotNull
    private Long isbn;
    @NotEmpty
    private String title;
    @NotNull
    private Year publishDate;
    @NotEmpty
    private String authorName;
    @NotEmpty
    private String authorSurname;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 10,fraction = 2, message = "price must be a valid monetary amount")
    private BigDecimal price;
    @NotNull
    private Long stock;

    public BookAuthorInventoryDto(BookDto book, Author author,Inventory inventory){
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.publishDate = book.getPublishDate();
        this.authorName = author.getAuthorName();
        this.authorSurname = author.getAuthorSurname();
        this.price = inventory.getPrice();
        this.stock = inventory.getStock();
    }
}
