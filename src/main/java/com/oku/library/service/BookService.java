package com.oku.library.service;

import com.oku.library.jpa.entity.Book;
import com.oku.library.jpa.repo.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepo bookRepo;

    public Optional<Book> getBookById(Long bookId) {
        return bookRepo.findById(bookId);
    }
}
