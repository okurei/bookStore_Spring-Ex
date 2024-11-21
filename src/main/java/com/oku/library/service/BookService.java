package com.oku.library.service;

import com.oku.library.controller.dto.BookDto;
import com.oku.library.controller.dto.IsbnOnly;
import com.oku.library.jpa.entity.Book;
import com.oku.library.jpa.repo.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepo bookRepo;
    @Autowired AuthorService authorService;

    public Optional<Book> getBookById(Long bookId) {
        return bookRepo.findById(bookId);
    }

    public ResponseEntity<List<Book>> addBooks(List<Book> bookList) {

        List<Book>bookLis = bookList.stream().map(this::checkBook).toList();
        List<Book> savedBook = bookRepo.saveAll(bookLis);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    private Book checkBook(Book book){
        BookDto bookDto = new BookDto(book);
        Optional<Book> bookOptional = bookRepo.findByIsbn(bookDto.getIsbn());
        if(bookOptional.isEmpty()){
            if (authorService.findAuthorId(bookDto.getAuthorId()) < 1){
                throw new RuntimeException("Author not found");
            }else return book;
        }
        throw new RuntimeException("Book already present");
    }

    public ResponseEntity<Optional<Book>> findByIsbn(Long isbn) {
        Optional<Book> book = bookRepo.findByIsbn(isbn);
        if (book.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(book, HttpStatus.FOUND);
        }
    }

    public ResponseEntity<BookDto> findByTitle(String title) {
        Optional<Book> optionalBook = bookRepo.findByTitle(title);
        Book book = optionalBook.orElseThrow(()-> new RuntimeException("Book not found"));
        BookDto bookDto = new BookDto(book);
        return ResponseEntity.ok(bookDto);
    }

    public ResponseEntity<List<BookDto>> findAllFromAuthor(String authorName) {
        List<Book>bookList = bookRepo.findAllBookOfAuthor(authorName);
        List<BookDto> bookDtoS = bookList.stream().map(BookDto::bookToDto).toList();
        return new ResponseEntity<>(bookDtoS, HttpStatus.FOUND);
    }

    public Book getBookByIsbn(Long isbn) {
        return bookRepo.getBookByIsbn(isbn);
    }

    public BookDto findBookByTitle (String title) {
        Optional<Book> optionalBook = bookRepo.findByTitle(title);
        Book book = optionalBook.orElseThrow(()-> new RuntimeException("Book not found"));
        return new BookDto(book);
    }

    public List<Long> getAllIsbn() {
        List<IsbnOnly> isbn = bookRepo.findAllBy();
        return isbn.stream().map(IsbnOnly::getIsbn).toList();
    }
}
