package com.oku.library.service;

import com.oku.library.jpa.entity.Author;
import com.oku.library.jpa.repo.AuthorRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepo authorRepo;

    public ResponseEntity<List<Author>> addAuthors(List<Author> authorList) {
        authorRepo.saveAll(authorList);
        return new ResponseEntity<>(authorList, HttpStatus.CREATED);
    }

    public ResponseEntity<Author> addAuthor(Author author) {
        authorRepo.save(author);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    public Author findById(Long authorId) {
        Optional<Author> authorOptional = authorRepo.findById(authorId);
        return authorOptional.orElseThrow(()-> new RuntimeException("Author not present"));
    }

    public int findAuthorId(Long authorId) {
        return authorRepo.findByIdCount(authorId);
    }
}
