package com.example.library.model;

import lombok.Data;

@Data
public class Book {
    private String id;
    private String title;
    private String isbn;
    private Author author;
    private Publisher publisher;
    private Cost cost;
}
