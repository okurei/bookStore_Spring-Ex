package com.oku.library.controller;


import com.oku.library.controller.dto.BookDto;
import com.oku.library.controller.dto.InventoryDto;
import com.oku.library.jpa.entity.Author;
import com.oku.library.jpa.entity.Book;
import com.oku.library.jpa.entity.Inventory;
import com.oku.library.service.AuthorService;
import com.oku.library.service.BookService;
import com.oku.library.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class Controller {

    @Autowired private BookService bookService;
    @Autowired private AuthorService authorService;
    @Autowired private InventoryService inventoryService;


    @GetMapping(path = "getBookById")
    public Optional<Book> getBookById(@Param("getBookById") Long bookId){
        return bookService.getBookById(bookId);
    }

    @PostMapping(path = "/addAuthor")
    public ResponseEntity<Author>addAuthor(@RequestBody Author author){
        return authorService.addAuthor(author);
    }

    @PostMapping(path = "/addAuthors")
    public ResponseEntity<List<Author>> addAuthor(@RequestBody List<Author> authorList){
        return authorService.addAuthors(authorList);
    }

    @PostMapping(path = "/addBooks")
    public ResponseEntity <List<Book>> addBooks(@RequestBody List<Book> bookList){
        return bookService.addBooks(bookList);
    }

    @GetMapping(path = "/findBookByIsbn/{isbn}")
    public ResponseEntity<Optional<Book>>findBookByIsbn(@PathVariable("isbn") Long isbn){
        return bookService.findByIsbn(isbn);
    }

    @GetMapping("/findBookByTitle/{title}")
    public ResponseEntity<BookDto> findBookByTitle(@PathVariable("title") String title){
        return bookService.findByTitle(title);
    }

    @GetMapping("/findAllBookOfAuthor/{authorName}")
    public ResponseEntity<List<BookDto>>findAllBookOfAuthor(@PathVariable("authorName")String authorName ){
        return bookService.findAllFromAuthor(authorName);
    }

    @PostMapping("/addBookToInventory/{isbn}")
    public ResponseEntity<Optional<Inventory>>addBookToInventory(@PathVariable("isbn")Long isbn, @RequestBody InventoryDto inventoryDto){
        return inventoryService.addBookToInventory(isbn, inventoryDto);
    }

    @GetMapping("/removeBookFromInventory/{isbn}")
    public ResponseEntity<Optional<Inventory>>removeBookFromInventory(@PathVariable("isbn")Long isbn){
        return inventoryService.removeBookFromInventory(isbn);
    }
}
