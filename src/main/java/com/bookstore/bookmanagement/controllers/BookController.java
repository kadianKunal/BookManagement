package com.bookstore.bookmanagement.controllers;

import com.bookstore.bookmanagement.entities.Book;
import com.bookstore.bookmanagement.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Retrieves all books.
     *
     * @return ResponseEntity containing the list of books
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }


    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return ResponseEntity containing the book if found, or 404 Not Found if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        try {
            Book book = bookService.getBookById(id);
            if (book != null) {
                return ResponseEntity.ok(book);
            } else {
                log.warn("Book not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Failed to fetch book with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Creates a new book.
     *
     * @param book the book to create
     * @return ResponseEntity containing the created book and 201 Created status
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book createdBook = bookService.createBook(book);
            log.info("Created book with ID: {}", createdBook.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
        } catch (Exception e) {
            log.error("Failed to create book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Updates an existing book.
     *
     * @param id   the ID of the book to update
     * @param book the updated book details
     * @return ResponseEntity containing the updated book if found, or 404 Not Found if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") int id, @RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateBook(id, book);
            if (updatedBook != null) {
                log.info("Updated book with ID: {}", updatedBook.getId());
                return ResponseEntity.ok(updatedBook);
            } else {
                log.warn("Book not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Failed to update book with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Deletes a book.
     *
     * @param id the ID of the book to delete
     * @return ResponseEntity with 204 No Content if the book is deleted, or 404 Not Found if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") int id) {
        try {
            boolean deleted = bookService.deleteBook(id);
            if (deleted) {
                log.info("Deleted book with ID: {}", id);
                return ResponseEntity.noContent().build();
            } else {
                log.error("Book not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("An error occurred while deleting the book with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
