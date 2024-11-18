package com.oku.library.controller;


import com.oku.library.jpa.entity.Book;
import com.oku.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/library")
public class Controller {

    @Autowired
    private BookService bookService;

    @GetMapping(path = "getBookById")
    public Optional<Book> getBookById(Long bookId){
        return bookService.getBookById(bookId);
    }
}
