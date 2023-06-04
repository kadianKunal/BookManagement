package com.bookstore.bookmanagement.controllers;

import com.bookstore.bookmanagement.entities.Book;
import com.bookstore.bookmanagement.models.BookDetail;
import com.bookstore.bookmanagement.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_ReturnsListOfBooks() {
        // Arrange
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", "Author 1", "Description 1", 10.0, 5));
        books.add(new Book(2, "Book 2", "Author 2", "Description 2", 15.0, 3));

        when(bookService.getAllBooks()).thenReturn(books);

        // Act
        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    @Test
    void getBookById_ExistingBookId_ReturnsBook() {
        // Arrange
        int bookId = 1;
        Book book = new Book(1, "Book 1", "Author 1", "Description 1", 10.0, 5);

        when(bookService.getBookById(bookId)).thenReturn(book);

        // Act
        ResponseEntity<Book> response = bookController.getBookById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void getBookById_NonExistingBookId_ReturnsNotFound() {
        // Arrange
        int bookId = 100;

        when(bookService.getBookById(bookId)).thenReturn(null);

        // Act
        ResponseEntity<Book> response = bookController.getBookById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getBookById_ExceptionThrown_ReturnsInternalServerError() {
        // Arrange
        int bookId = 1;

        when(bookService.getBookById(bookId)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<Book> response = bookController.getBookById(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void orderBooks_ValidBookDetails_ReturnsSuccess() {
        // Arrange
        List<BookDetail> bookDetailList = new ArrayList<>();
        bookDetailList.add(new BookDetail(1, 2));
        bookDetailList.add(new BookDetail(2, 3));

        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book 1", "Author 1", "Description 1", 10.0, 5));
        books.add(new Book(2, "Book 2", "Author 2", "Description 2", 15.0, 3));

        when(bookService.orderBooks(bookDetailList)).thenReturn(books);

        // Act
        ResponseEntity<Object> response = bookController.orderBooks(bookDetailList);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }

    @Test
    void orderBooks_ExceptionThrown_ReturnsInternalServerError() {
        // Arrange
        List<BookDetail> bookDetailList = new ArrayList<>();
        bookDetailList.add(new BookDetail(1, 2));
        bookDetailList.add(new BookDetail(2, 3));

        when(bookService.orderBooks(bookDetailList)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<Object> response = bookController.orderBooks(bookDetailList);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void returnBooks_ValidBookDetails_ReturnsSuccess() {
        // Arrange
        List<BookDetail> bookDetailList = new ArrayList<>();
        bookDetailList.add(new BookDetail(1, 2));
        bookDetailList.add(new BookDetail(2, 3));


        // Act
        ResponseEntity<Boolean> response = bookController.returnBooks(bookDetailList);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void returnBooks_ExceptionThrown_ReturnsInternalServerError() {
        // Arrange
        List<BookDetail> bookDetailList = new ArrayList<>();
        bookDetailList.add(new BookDetail(1, 2));
        bookDetailList.add(new BookDetail(2, 3));

        doThrow(RuntimeException.class).when(bookService).returnBooks(bookDetailList);

        // Act
        ResponseEntity<Boolean> response = bookController.returnBooks(bookDetailList);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    void createBook_ValidBook_ReturnsCreatedBook() {
        // Arrange
        Book book = new Book(1, "Book 1", "Author 1", "Description 1", 10.0, 5);

        when(bookService.createBook(book)).thenReturn(book);

        // Act
        ResponseEntity<Book> response = bookController.createBook(book);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void createBook_ExceptionThrown_ReturnsInternalServerError() {
        // Arrange
        Book book = new Book(1, "Book 1", "Author 1", "Description 1", 10.0, 5);

        when(bookService.createBook(book)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<Book> response = bookController.createBook(book);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updateBook_ExistingBook_ReturnsUpdatedBook() {
        // Arrange
        int bookId = 1;
        Book book = new Book(1, "Updated Book", "Updated Author", "Updated Description", 20.0, 10);

        when(bookService.updateBook(bookId, book)).thenReturn(book);

        // Act
        ResponseEntity<Book> response = bookController.updateBook(bookId, book);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    void updateBook_NonExistingBook_ReturnsNotFound() {
        // Arrange
        int bookId = 100;
        Book book = new Book(100, "Updated Book", "Updated Author", "Updated Description", 20.0, 10);

        when(bookService.updateBook(bookId, book)).thenReturn(null);

        // Act
        ResponseEntity<Book> response = bookController.updateBook(bookId, book);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateBook_ExceptionThrown_ReturnsInternalServerError() {
        // Arrange
        int bookId = 1;
        Book book = new Book(1, "Updated Book", "Updated Author", "Updated Description", 20.0, 10);

        when(bookService.updateBook(bookId, book)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<Book> response = bookController.updateBook(bookId, book);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deleteBook_ExistingBook_ReturnsNoContent() {
        // Arrange
        int bookId = 1;

        when(bookService.deleteBook(bookId)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = bookController.deleteBook(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteBook_NonExistingBook_ReturnsNotFound() {
        // Arrange
        int bookId = 100;

        when(bookService.deleteBook(bookId)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = bookController.deleteBook(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteBook_ExceptionThrown_ReturnsInternalServerError() {
        // Arrange
        int bookId = 1;

        when(bookService.deleteBook(bookId)).thenThrow(RuntimeException.class);

        // Act
        ResponseEntity<Void> response = bookController.deleteBook(bookId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
