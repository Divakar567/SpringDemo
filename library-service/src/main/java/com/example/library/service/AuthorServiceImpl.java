package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.getAuthors();
    }

    @Override
    public Author getAuthor(String id) {
        return authorRepository.getAuthor(id);
    }

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.saveAuthor(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        if (author.getId() == null || "".equals(author.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author id is required");
        }
        return authorRepository.updateAuthor(author);
    }

    @Override
    public Author deleteAuthor(String id) {
        Author author = this.getAuthor(id);
        if (author != null) {
            authorRepository.deleteAuthor(id);
        }
        return author;
    }
}
