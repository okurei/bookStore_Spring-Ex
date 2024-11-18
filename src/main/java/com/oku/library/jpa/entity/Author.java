package com.oku.library.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Author {
    @Id
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    @Column(name = "authorId")
    private Long authorId;
    @Column(name = "authorName", columnDefinition = "TEXT")
    private String authorName;
    @Column(name = "authorSurname", columnDefinition = "TEXT")
    private String authorSurname;
    @Column(name = "dob", columnDefinition = "TEXT")
    private String dob;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;
        return Objects.equals(authorId, author.authorId) && Objects.equals(authorName, author.authorName) && Objects.equals(authorSurname, author.authorSurname) && Objects.equals(dob, author.dob);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(authorId);
        result = 31 * result + Objects.hashCode(authorName);
        result = 31 * result + Objects.hashCode(authorSurname);
        result = 31 * result + Objects.hashCode(dob);
        return result;
    }
}
