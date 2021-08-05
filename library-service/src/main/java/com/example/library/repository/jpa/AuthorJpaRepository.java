package com.example.library.repository.jpa;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.jpa.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

public interface AuthorJpaRepository extends AuthorRepository, JpaRepository<AuthorEntity, String> {
    default List<Author> getAuthors() {
        return this.findAll().stream()
                .map(AuthorEntity::toAuthor)
                .collect(Collectors.toList());
    }

    default Author getAuthor(String id) {
        return this.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"))
                .toAuthor();
    }

    default Author saveAuthor(Author author) {
        return this.save(AuthorEntity.from(author)).toAuthor();
    }

    default Author updateAuthor(Author author) {
        AuthorEntity authorEntity = this.findById(author.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
        return this.save(authorEntity.update(author)).toAuthor();
    }

    default void deleteAuthor(String id) {
        this.deleteById(id);
    }

    AuthorEntity save(AuthorEntity entity);
}
