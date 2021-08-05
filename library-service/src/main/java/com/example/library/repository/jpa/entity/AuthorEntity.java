package com.example.library.repository.jpa.entity;

import com.example.library.model.Author;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity(name = "Author")
public class AuthorEntity {
    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BookEntity> books;

    public static AuthorEntity from(Author author) {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(author.getId());
        entity.setName(author.getName());
        return entity;
    }

    public AuthorEntity update(Author author) {
        this.setName(author.getName());
        return this;
    }

    public Author toAuthor() {
        Author author = new Author();
        author.setId(this.getId());
        author.setName(this.getName());
        return author;
    }

    @PrePersist
    public void checkId() {
        if(StringUtils.isEmpty(this.getId())) {
            this.setId(UUID.randomUUID().toString());
        }
    }
}
