package com.bookstore.bookmanagement.services;

import com.bookstore.bookmanagement.entities.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Component
public class BookService {

    private final List<Book> books;

    public BookService() {
        this.books = new ArrayList<>();
    }

    /**
     * Retrieves all books.
     *
     * @return the list of books
     */
    public List<Book> getAllBooks() {
        return books;
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return the book if found, or null if not found
     */
    public Book getBookById(int id) {
        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
        return optionalBook.orElse(null);
    }

    /**
     * Creates a new book.
     *
     * @param book the book to create
     * @return the created book
     */
    public Book createBook(Book book) {
        book.setId(generateNextId());
        books.add(book);
        return book;
    }

    /**
     * Updates an existing book.
     *
     * @param id           the ID of the book to update
     * @param updatedBook  the updated book details
     * @return the updated book if found, or null if not found
     */
    public Book updateBook(int id, Book updatedBook) {
        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            updatedBook.setId(id);
            int index = books.indexOf(book);
            books.set(index, updatedBook);
            log.info("Updated book with ID: {}", id);
            return updatedBook;
        } else {
            log.warn("Book not found with ID: {}", id);
            return null;
        }
    }

    /**
     * Deletes a book.
     *
     * @param id the ID of the book to delete
     * @return true if the book is deleted, false if not found
     */
    public boolean deleteBook(int id) {
        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            books.remove(book);
            return true;
        } else {
            log.warn("Book not found with ID: {}", id);
            return false;
        }
    }

    private int generateNextId() {
        int maxId = books.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }

}
