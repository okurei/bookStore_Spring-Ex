package com.oku.library.service;

import com.oku.library.controller.dto.BookDto;
import com.oku.library.jpa.entity.Book;
import com.oku.library.jpa.repo.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepo bookRepo;

    public Optional<Book> getBookById(Long bookId) {
        return bookRepo.findById(bookId);
    }

    public ResponseEntity<List<Book>> addBooks(List<Book> bookList) {
        List<Book> savedBook = bookRepo.saveAll(bookList);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    public ResponseEntity<Optional<Book>> findByIsbn(Long isbn) {
        Optional<Book> book = bookRepo.findByIsbn(isbn);
        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }

    public ResponseEntity<BookDto> findByTitle(String title) {
        Optional<Book> optionalBook = bookRepo.findByTitle(title);
        Book book = optionalBook.orElseThrow(()-> new RuntimeException("Book not found"));
        BookDto bookDto = new BookDto(book);
        return ResponseEntity.ok(bookDto);
    }

    public ResponseEntity<List<BookDto>> findAllFromAuthor(String authorName) {
        List<Book>bookList = bookRepo.findAllBookOfAuthor(authorName);
        List<BookDto> bookDtos = bookList.stream().map(BookDto::bookToDto).toList();
        return new ResponseEntity<>(bookDtos, HttpStatus.FOUND);
    }
}
