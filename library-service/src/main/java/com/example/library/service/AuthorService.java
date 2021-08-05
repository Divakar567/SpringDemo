package com.example.library.service;

import com.example.library.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAuthors();

    Author getAuthor(String id);

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    Author deleteAuthor(String id);
}
