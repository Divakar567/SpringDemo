package com.example.library.repository.jpa.entity;

import com.example.library.model.Book;
import com.example.library.model.Cost;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity(name = "Book")
public class BookEntity {
    @Id
    private String id;
    private String title;
    private String isbn;
    @Column(name = "author_id", nullable = false)
    private String authorId;
    @Column(name = "publisher_id", nullable = false)
    private String publisherId;
    private CostEntity cost;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "author_id_fk"), insertable = false, updatable = false)
    private AuthorEntity author;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id", foreignKey = @ForeignKey(name = "publisher_id_fk"), insertable = false, updatable = false)
    private PublisherEntity publisher;

    public static BookEntity from(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.getId());
        entity.setTitle(book.getTitle());
        entity.setIsbn(book.getIsbn());
        if(Objects.nonNull(book.getAuthor())) {
            entity.setAuthorId(book.getAuthor().getId());
        }
        if(Objects.nonNull(book.getPublisher())) {
            entity.setPublisherId(book.getPublisher().getId());
        }
        entity.setCost(CostEntity.from(book.getCost()));
        return entity;
    }

    public BookEntity update(Book book) {
        this.setTitle(book.getTitle());
        this.setIsbn(book.getIsbn());
        this.setAuthorId(book.getAuthor().getId());
        this.setPublisherId(book.getPublisher().getId());
        this.getCost().update(book.getCost());
        return this;
    }

    public Book toBook() {
        Book book = new Book();
        book.setId(this.getId());
        book.setTitle(this.getTitle());
        book.setIsbn(this.getIsbn());
        if(this.getAuthor() != null) {
            book.setAuthor(this.getAuthor().toAuthor());
        }
        if(this.getPublisher() != null) {
            book.setPublisher(this.getPublisher().toPublisher());
        }
        book.setCost(this.getCost().toBook());
        return book;
    }

    @PrePersist
    public void checkId() {
        if(StringUtils.isEmpty(this.getId())) {
            this.setId(UUID.randomUUID().toString());
        }
    }
}
