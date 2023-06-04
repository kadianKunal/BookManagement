package com.bookstore.bookmanagement.services;

import com.bookstore.bookmanagement.dao.BookRepository;
import com.bookstore.bookmanagement.entities.Book;
import com.bookstore.bookmanagement.models.BookDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_ShouldReturnListOfBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "book title 1", "book author 1",
                "book description 1", 100, 10));
        books.add(new Book(2, "book title 2", "book author 2",
                "book description 2", 150, 5));

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("book title 1", result.get(0).getTitle());
        assertEquals("book title 2", result.get(1).getTitle());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById_ExistingBookId_ShouldReturnBook() {
        int bookId = 1;
        Book book = new Book(1, "book title 1", "book author 1",
                "book description 1", 100, 10);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(bookId);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("book title 1", result.getTitle());

        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void getBookById_NonExistingBookId_ShouldReturnNull() {
        int bookId = 100;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Book result = bookService.getBookById(bookId);

        assertNull(result);

        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void createBook_ShouldReturnCreatedBook() {
        Book book = new Book(1, "book title 1", "book author 1",
                "book description 1", 100, 10);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.createBook(book);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("book title 1", result.getTitle());

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_ExistingBookId_ShouldReturnUpdatedBook() {
        int bookId = 1;
        Book existingBook = new Book(1, "book title 1", "book author 1",
                "book description 1", 100, 10);
        Book updatedBook = new Book(1, "updated book title 1", "book author 1",
                "book description 1", 100, 15);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(bookId, updatedBook);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("updated book title 1", result.getTitle());

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(updatedBook);
    }

    @Test
    void updateBook_NonExistingBookId_ShouldReturnNull() {
        int bookId = 100;
        Book updatedBook = new Book(1, "updated book title 1", "book author 1",
                "book description 1", 100, 10);

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Book result = bookService.updateBook(bookId, updatedBook);

        assertNull(result);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(0)).save(updatedBook);
    }

    @Test
    void returnBooks_ValidBookDetailsList_ShouldUpdateInventory() {
        List<BookDetail> bookDetailList = new ArrayList<>();
        bookDetailList.add(new BookDetail(1, 5));
        bookDetailList.add(new BookDetail(2, 3));

        Book existingBook1 =new Book(1, "book title 1", "book author 1",
                "book description 1", 100, 10);
        Book existingBook2 = new Book(2, "book title 2", "book author 2",
                "book description 2", 150, 5);

        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook1));
        when(bookRepository.findById(2)).thenReturn(Optional.of(existingBook2));

        bookService.returnBooks(bookDetailList);

        assertEquals(15, existingBook1.getQuantity());
        assertEquals(8, existingBook2.getQuantity());

        verify(bookRepository, times(2)).save(any(Book.class));
    }

    @Test
    void returnBooks_InvalidBookDetailsList_ShouldThrowException() {
        List<BookDetail> bookDetailList = new ArrayList<>();
        bookDetailList.add(new BookDetail(1, 5));
        bookDetailList.add(new BookDetail(2, 3));

        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bookService.returnBooks(bookDetailList));

        verify(bookRepository, times(1)).findById(anyInt());
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    @Test
    void orderBooks_AllBooksInStock_ShouldProcessOrderAndReturnOrderedBooks() {
        List<BookDetail> bookDetailList = new ArrayList<>();
        bookDetailList.add(new BookDetail(1, 5));
        bookDetailList.add(new BookDetail(2, 3));

        Book existingBook1 = new Book(1, "book title 1", "book author 1",
                "book description 1", 100, 10);
        Book existingBook2 = new Book(2, "book title 2", "book author 2",
                "book description 2", 150, 5);

        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook1));
        when(bookRepository.findById(2)).thenReturn(Optional.of(existingBook2));

        List<Book> orderedBooks = bookService.orderBooks(bookDetailList);

        assertEquals(5, existingBook1.getQuantity());
        assertEquals(2, existingBook2.getQuantity());
        assertEquals(2, orderedBooks.size());

        verify(bookRepository, times(2)).save(any(Book.class));
    }

    @Test
    void orderBooks_BookNotInStock_ShouldThrowException() {
        List<BookDetail> bookDetailList = new ArrayList<>();
        bookDetailList.add(new BookDetail(1, 15));

        Book existingBook1 = new Book(1, "book title 1", "book author 1",
                "book description 1", 100, 10);

        when(bookRepository.findById(1)).thenReturn(Optional.of(existingBook1));

        assertThrows(IllegalArgumentException.class, () -> bookService.orderBooks(bookDetailList));

        verify(bookRepository, times(1)).findById(anyInt());
        verify(bookRepository, times(0)).save(any(Book.class));
    }

    @Test
    void deleteBook_ExistingBookId_ShouldReturnTrue() {
        int bookId = 1;
        Book existingBook =new Book(1, "book title 1", "book author 1",
                "book description 1", 100, 10);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        boolean result = bookService.deleteBook(bookId);

        assertTrue(result);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).delete(existingBook);
    }

    @Test
    void deleteBook_NonExistingBookId_ShouldReturnFalse() {
        int bookId = 100;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        boolean result = bookService.deleteBook(bookId);

        assertFalse(result);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(0)).delete(any(Book.class));
    }
}