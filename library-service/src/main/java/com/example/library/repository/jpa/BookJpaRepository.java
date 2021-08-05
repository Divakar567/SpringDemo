package com.example.library.repository.jpa;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.jpa.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

public interface BookJpaRepository extends BookRepository, JpaRepository<BookEntity, String> {
    default List<Book> getBooks() {
        return this.findAll().stream()
                .map(BookEntity::toBook)
                .collect(Collectors.toList());
    }

    default Book getBook(String id) {
        return this.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"))
                .toBook();
    }

    default Book saveBook(Book book) {
        return this.save(BookEntity.from(book)).toBook();
    }

    default Book updateBook(Book book) {
        BookEntity bookEntity = this.findById(book.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        return this.save(bookEntity.update(book)).toBook();
    }

    default void deleteBook(String id) {
        this.deleteById(id);
    }
}
