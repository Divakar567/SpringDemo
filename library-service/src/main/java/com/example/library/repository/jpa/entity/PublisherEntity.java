package com.example.library.repository.jpa.entity;

import com.example.library.model.Publisher;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "Publisher")
public class PublisherEntity {
    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BookEntity> books;

    public static PublisherEntity from(Publisher publisher) {
        PublisherEntity entity = new PublisherEntity();
        entity.setId(publisher.getId());
        entity.setName(publisher.getName());
        return entity;
    }

    public PublisherEntity update(Publisher publisher) {
        this.setName(publisher.getName());
        return this;
    }

    public Publisher toPublisher() {
        Publisher publisher = new Publisher();
        publisher.setId(this.getId());
        publisher.setName(this.getName());
        return publisher;
    }

    @PrePersist
    public void checkId() {
        if(StringUtils.isEmpty(this.getId())) {
            this.setId(UUID.randomUUID().toString());
        }
    }
}
