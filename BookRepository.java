package com.library.BookLibrary.repository;

import com.library.BookLibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    //custom query methods using Spring Data JPA
    List<Book> findByAuthorContainingIgnoreCase(String authorName);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByGenreContainingIgnoreCase(String genre);
    List<Book> findByPublicationYear(int publicationYear);
    List<Book> findByPublicationContainingIgnoreCase(String publication);

}
