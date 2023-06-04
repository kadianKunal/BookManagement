package com.bookstore.bookmanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDetail {
    private int bookId;
    private int orderedQuantity;
}
