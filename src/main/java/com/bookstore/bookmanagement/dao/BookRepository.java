package com.bookstore.bookmanagement.dao;

import com.bookstore.bookmanagement.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> {
    // add custom methods here
}
