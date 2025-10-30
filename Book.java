package com.library.BookLibrary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String publication;

    @Column(name = "publication_year")
    private Integer publicationYear;

    private String genre;

    @Column(name = "available_copies")
    private Integer availableCopies;
}
