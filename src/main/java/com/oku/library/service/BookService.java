package com.oku.library.service;

import com.oku.library.controller.dto.BookDto;
import com.oku.library.controller.dto.IsbnOnly;
import com.oku.library.exception.FKConstrainViolation;
import com.oku.library.jpa.entity.Book;
import com.oku.library.jpa.repo.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepo bookRepo;
    @Autowired private AuthorService authorService;

    public ResponseEntity<List<BookDto>> addBooks(List<Book> bookList) {
        Map<Boolean, List<Book>> listMap = bookList.stream().collect(Collectors.partitioningBy(this::checkBooks));

        List<Book> discardBookList = listMap.get(false);

        if(!discardBookList.isEmpty()){
            List<Long>isbn = discardBookList.stream().map(Book::getIsbn).toList();
            throw new FKConstrainViolation("Author not present in the db for the following book: " + isbn);
        }

        List<Book> filteredBookList = listMap.get(true);
        if(filteredBookList.isEmpty()) {
            throw new RuntimeException("No book to save");
        }else {
            bookRepo.saveAll(filteredBookList);
            List<BookDto> bookDtoList = filteredBookList.stream().map(BookDto::bookToDto).toList();
            return ResponseEntity.ok(bookDtoList);
        }
    }

    private Boolean checkBooks(Book book){
        if(bookRepo.findByIsbn(book.getIsbn()).isEmpty()){
            return authorService.findIfPresent(book.getAuthor());
        } return false;
    }

    public BookDto getBookById(Long bookId) {
        return new BookDto (bookRepo.findById(bookId).orElseThrow());
    }

    public BookDto findByIsbn(Long isbn) {
        return new BookDto(bookRepo.findByIsbn(isbn).orElseThrow());
    }

    public BookDto findByTitle(String title) {
        return new BookDto(bookRepo.findByTitle(title).orElseThrow());
    }

    public List<BookDto> findAllFromAuthor(String authorName) {
        return bookRepo.findAllBookOfAuthor(authorName).stream().map(BookDto::bookToDto).toList();
    }

    public List<Long> getAllIsbn() {
        List<IsbnOnly> isbn = bookRepo.findAllBy();
        return isbn.stream().map(IsbnOnly::getIsbn).toList();
    }

    public Page<Book> getAllBook(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title"));
        return bookRepo.findAll(pageable);
    }

    public Page<Book> getBookByPublishYear(int page, int size, Year initialDate, Year endDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishDate"));
        return bookRepo.findAllByPublishDate(pageable, initialDate, endDate);
    }
}
