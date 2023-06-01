package com.bookstore.bookmanagement.services;

import com.bookstore.bookmanagement.dao.BookRepository;
import com.bookstore.bookmanagement.entities.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Component
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Retrieves all books.
     *
     * @return the list of books
     */
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book to retrieve
     * @return the book if found, or null if not found
     */
    public Book getBookById(int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    /**
     * Creates a new book.
     *
     * @param book the book to create
     * @return the created book
     */
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Updates an existing book.
     *
     * @param id          the ID of the book to update
     * @param updatedBook the updated book details
     * @return the updated book if found, or null if not found
     */
    public Book updateBook(int id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            updatedBook.setId(id);
            return bookRepository.save(updatedBook);
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
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            bookRepository.delete(optionalBook.get());
            return true;
        } else {
            log.warn("Book not found with ID: {}", id);
            return false;
        }
    }

}
