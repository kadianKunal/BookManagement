package com.bookstore.bookmanagement.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private int id;

    @NonNull
    private String title;

    private String author;

    private String description;

    @NonNull
    private double price;

    @NonNull
    private int quantity;
}
