package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.util.annotation.Searchable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }

    @Override
    public Book getBook(String id) {
        return bookRepository.getBook(id);
    }

    @Searchable(topic = "es-books-topic", index = "books")
    @Transactional
    @Override
    public Book saveBook(Book book) {
        return bookRepository.saveBook(book);
    }

    @Searchable(topic = "es-books-topic", index = "books")
    @Transactional
    @Override
    public Book updateBook(Book book) {
        if (book.getId() == null || "".equals(book.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book id is required");
        }
        return bookRepository.updateBook(book);
    }

    @Searchable(topic = "es-books-topic", index = "books")
    @Transactional
    @Override
    public Book deleteBook(String id) {
        Book book = this.getBook(id);
        if (book != null) {
            bookRepository.deleteBook(id);
        }
        return book;
    }
}
