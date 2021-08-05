package com.example.library.repository;

import com.example.library.model.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> getAuthors();

    Author getAuthor(String id);

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthor(String id);
}
