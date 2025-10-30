package com.library.BookLibrary.controller;

import com.library.BookLibrary.model.Book;
import com.library.BookLibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // 1. POST: add a new book to the library
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        try{
            Book savedBook = bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook); 
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    // 2. GET: get all books in the library
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    // 3. GET: get a specific book by ID 
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. PUT: update a specific book's information
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    // Update fields if provided
                    if (updatedBook.getTitle() != null) {
                        existingBook.setTitle(updatedBook.getTitle());
                    }
                    if (updatedBook.getAuthor() != null) {
                        existingBook.setAuthor(updatedBook.getAuthor());
                    }
                    if (updatedBook.getPublication() != null) {
                        existingBook.setPublication(updatedBook.getPublication());
                    }
                    if (updatedBook.getPublicationYear() != null) {
                        existingBook.setPublicationYear(updatedBook.getPublicationYear());
                    }
                    if (updatedBook.getGenre() != null) {
                        existingBook.setGenre(updatedBook.getGenre());
                    }
                    if (updatedBook.getAvailableCopies() != null) {
                        existingBook.setAvailableCopies(updatedBook.getAvailableCopies());
                    }

                    Book savedBook = bookRepository.save(existingBook);
                    return ResponseEntity.ok(savedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. DELETE: remove a book from library 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // 6. GET: find all books by a specific author 
    @GetMapping("/author/{authorName}")
    public ResponseEntity<List<Book>> getBooksByAuthorName(@PathVariable String authorName){
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(authorName);
        return ResponseEntity.ok(books);
    }

    // 7. GET: find all books in a specific genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String genre){
        List<Book> books = bookRepository.findByGenreContainingIgnoreCase(genre);
        return ResponseEntity.ok(books);
    }

    // 8. GET: get all books by publication
    @GetMapping("/publication/{publication}")
    public ResponseEntity<List<Book>> getBooksByPublication(@PathVariable String publication){
        List<Book> books = bookRepository.findByPublicationContainingIgnoreCase(publication);
        return ResponseEntity.ok(books);
    }

    // 9. GET: search by title
    @GetMapping("/search")
    public ResponseEntity<List<Book>> getBooksBySearch(@RequestParam String title){
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        return ResponseEntity.ok(books);
    }

}
