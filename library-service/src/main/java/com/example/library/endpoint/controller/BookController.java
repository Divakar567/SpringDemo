package com.example.library.endpoint.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        Book book = bookService.getBook(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        book = bookService.saveBook(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        book = bookService.updateBook(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable String id) {
        Book book = bookService.deleteBook(id);
        return ResponseEntity.ok(book);
    }
}
