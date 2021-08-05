package com.example.library.service;

import com.example.library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBook(String id);

    Book saveBook(Book book);

    Book updateBook(Book book);

    Book deleteBook(String id);
}
