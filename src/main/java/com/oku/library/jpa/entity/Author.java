package com.oku.library.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    @Column(name = "authorId")
    private Long authorId;
    @Column(name = "authorName")
    private String authorName;
    @Column(name = "authorSurname")
    private String authorSurname;
    @Column(name = "dob")
    private String dob;


}
