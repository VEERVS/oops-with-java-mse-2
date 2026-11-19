
package com.example.library.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.model.Book;
import com.example.library.service.BookService;

// Q2 (b), (c), (d), (e): REST CONTROLLER

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // Q2 (b): ADD BOOK - POST
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        Book saved = service.addBook(book);

        if (saved == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Q2 (c): GET ALL BOOKS
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {

        return ResponseEntity.ok(service.getAllBooks());
    }

    // Q2 (c): GET BOOK BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @PathVariable Integer id) {

        Book book = service.getBookById(id);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    // Q2 (d): UPDATE BOOK - PUT
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer id,
            @RequestBody Book book) {

        Book updated = service.updateBook(id, book);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // Q2 (e): DELETE BOOK - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable Integer id) {

        boolean deleted = service.deleteBook(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
