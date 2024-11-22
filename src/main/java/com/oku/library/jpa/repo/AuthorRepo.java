package com.oku.library.jpa.repo;


import com.oku.library.jpa.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {

    @Query("select count(*) from Author a where a.authorId = :authorId")
    int findByIdCount(@Param("authorId") Long authorId);
}
