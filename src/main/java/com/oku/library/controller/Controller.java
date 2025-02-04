package com.oku.library.controller;


import com.oku.library.controller.dto.BookAuthorInventoryDto;
import com.oku.library.controller.dto.BookDto;
import com.oku.library.controller.dto.InventoryDto;
import com.oku.library.jpa.entity.Author;
import com.oku.library.jpa.entity.Book;
import com.oku.library.jpa.entity.Inventory;
import com.oku.library.service.AuthorService;
import com.oku.library.service.BookAuthorInventoryService;
import com.oku.library.service.BookService;
import com.oku.library.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class Controller {

    @Autowired private BookService bookService;
    @Autowired private AuthorService authorService;
    @Autowired private InventoryService inventoryService;
    @Autowired private BookAuthorInventoryService bookAuthorInventoryService;


    @GetMapping(path = "/getBookById/{id}")
    public ResponseEntity<BookDto> getBookById(@Param("id") Long bookId){
        return new ResponseEntity<>(bookService.getBookById(bookId), HttpStatus.FOUND);
    }

    @PostMapping(path = "/addAuthors")
    public ResponseEntity<List<Author>> addAuthors(@RequestBody List<Author> authorList){
        return authorService.addAuthors(authorList);
    }

    @PostMapping(path = "/addBooks")
    public ResponseEntity <List<BookDto>> addBooks(@RequestBody List<Book> bookList){
        return bookService.addBooks(bookList);
    }

    @GetMapping(path = "/findBookByIsbn/{isbn}")
    public ResponseEntity<BookDto> findBookByIsbn(@PathVariable("isbn") Long isbn){
        return new ResponseEntity<>(bookService.findByIsbn(isbn), HttpStatus.FOUND);
    }

    @GetMapping("/findBookByTitle/{title}")
    public ResponseEntity<BookDto> findBookByTitle(@PathVariable("title") String title){
        return new ResponseEntity<>(bookService.findByTitle(title), HttpStatus.FOUND);
    }

    @GetMapping("/findAllBookOfAuthor/{authorName}")
    public ResponseEntity<List<BookDto>>findAllBookOfAuthor(@PathVariable("authorName")String authorName ){
        return new ResponseEntity<>(bookService.findAllFromAuthor(authorName), HttpStatus.FOUND);
    }

    @PostMapping("/addBookToInventory/{isbn}")
    public ResponseEntity<Optional<Inventory>>addBookToInventory(@PathVariable("isbn")Long isbn, @RequestBody InventoryDto inventoryDto){
        return inventoryService.addBookToInventory(isbn, inventoryDto);
    }

    @GetMapping("/removeBookFromInventory/{isbn}")
    public ResponseEntity<Optional<Inventory>>removeBookFromInventory(@PathVariable("isbn")Long isbn){
        return inventoryService.removeBookFromInventory(isbn);
    }


    @GetMapping("getBookAuthorInventory/{title}")
    public ResponseEntity<BookAuthorInventoryDto>getBookAuthorInventory(@PathVariable("title")String title){
        return bookAuthorInventoryService.getBookAuthorInventory(title);
    }

    @GetMapping("getAllIsbn")

    public ResponseEntity<List<Long>>getAllIsbn(){
        return new ResponseEntity<>(bookService.getAllIsbn(),HttpStatus.FOUND);
    }

    @GetMapping("populateInventory")
    public ResponseEntity<List<Inventory>> populateInventory(){
        return inventoryService.populateInventory();
    }

    @GetMapping("getBookAuthorInventoryOneQuery/{title}")
    public ResponseEntity<Optional<BookAuthorInventoryDto>>getBookAuthorInventoryDtoOneQuery(@PathVariable("title")String title){
        return bookAuthorInventoryService.getBookAuthorInventoryOneQuery(title);
    }

    @GetMapping("getBooksPage")
    public ResponseEntity<Page<Book>> getBooksPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<Book> books = bookService.getAllBook(page, size);
        return ResponseEntity.ok(books);
    }

    @GetMapping("getBookByPublishDate")
    public ResponseEntity<Page<Book>> getBookByPublishDate(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0")Year initialDate,
            @RequestParam(defaultValue = "9999") Year endDate){
        Page<Book> books = bookService.getBookByPublishYear(page, size, initialDate,endDate);
        return ResponseEntity.ok(books);
    }

    @GetMapping("getBookWhitPriceRange")
    public ResponseEntity<List<Inventory>> getBookPrice(
            @RequestParam(defaultValue = "0")BigDecimal priceLower,
            @RequestParam(defaultValue = "9999")BigDecimal priceUpper){
        return ResponseEntity.ok(inventoryService.getBookPriceRange(priceLower, priceUpper));
    }
}
