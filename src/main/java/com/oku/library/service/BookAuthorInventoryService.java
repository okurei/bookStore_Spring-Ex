package com.oku.library.service;

import com.oku.library.controller.dto.BookAuthorInventoryDto;
import com.oku.library.controller.dto.BookDto;
import com.oku.library.jpa.entity.Author;
import com.oku.library.jpa.entity.Inventory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookAuthorInventoryService {

    @Autowired BookService bookService;
    @Autowired InventoryService inventoryService;
    @Autowired AuthorService authorService;

    public ResponseEntity<BookAuthorInventoryDto> getBookAuthorInventory(String title) {
        BookDto bookDto = bookService.findBookByTitle(title);
        Inventory inventory = inventoryService.findBookInInventory(bookDto.getIsbn());
        Author author = authorService.findById(bookDto.getAuthorId());
        return ResponseEntity.ok(new BookAuthorInventoryDto(bookDto,author,inventory));
    }
}
