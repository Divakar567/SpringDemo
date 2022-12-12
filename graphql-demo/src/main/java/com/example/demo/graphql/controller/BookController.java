package com.example.demo.graphql.controller;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
public class BookController {

    private static final List<Book> books = Arrays.asList(
            new Book("book-1", "Harry Potter and the Philosopher's Stone", 223, "author-1"),
            new Book("book-2", "Moby Dick", 635, "author-2"),
            new Book("book-3", "Interview with the vampire", 371, "author-3")
    );

    private static final List<Author> authors = Arrays.asList(
            new Author("author-1", "Joanne", "Rowling"),
            new Author("author-2", "Herman", "Melville"),
            new Author("author-3", "Anne", "Rice")
    );

    @QueryMapping
    public Book bookById(@Argument String id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst().orElse(null);
    }

    @SchemaMapping
    public Author author(Book book) {
        return authors.stream()
                .filter(author -> author.getId().equals(book.getAuthorId()))
                .findFirst().orElse(null);
    }
}
