package com.example.library.repository;

import com.example.library.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getBooks();

    Book getBook(String id);

    Book saveBook(Book book);

    Book updateBook(Book book);

    void deleteBook(String id);
}
