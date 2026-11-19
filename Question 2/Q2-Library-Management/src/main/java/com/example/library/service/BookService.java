
package com.example.library.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.library.model.Book;

// BUSINESS LOGIC USING ARRAYLIST

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();

    // Add Book
    public Book addBook(Book book) {

        for (Book b : books) {
            if (b.getId().equals(book.getId())) {
                return null;
            }
        }

        books.add(book);
        return book;
    }

    // Get All Books
    public List<Book> getAllBooks() {
        return books;
    }

    // Get Book By ID
    public Book getBookById(Integer id) {

        for (Book b : books) {
            if (b.getId().equals(id)) {
                return b;
            }
        }

        return null;
    }

    // Update Book
    public Book updateBook(Integer id, Book updated) {

        Book existing = getBookById(id);

        if (existing == null) {
            return null;
        }

        existing.setTitle(updated.getTitle());
        existing.setAuthor(updated.getAuthor());
        existing.setCategory(updated.getCategory());
        existing.setAvailableCopies(updated.getAvailableCopies());

        return existing;
    }

    // Delete Book
    public boolean deleteBook(Integer id) {

        return books.removeIf(b -> b.getId().equals(id));
    }
}
