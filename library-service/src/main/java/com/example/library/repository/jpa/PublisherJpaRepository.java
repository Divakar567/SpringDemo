package com.example.library.repository.jpa;

import com.example.library.model.Publisher;
import com.example.library.repository.PublisherRepository;
import com.example.library.repository.jpa.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

public interface PublisherJpaRepository extends PublisherRepository, JpaRepository<PublisherEntity, String> {
    default List<Publisher> getPublishers() {
        return this.findAll().stream()
                .map(PublisherEntity::toPublisher)
                .collect(Collectors.toList());
    }

    default Publisher getPublisher(String id) {
        return this.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"))
                .toPublisher();
    }

    default Publisher savePublisher(Publisher publisher) {
        return this.save(PublisherEntity.from(publisher)).toPublisher();
    }

    default Publisher updatePublisher(Publisher publisher) {
        PublisherEntity publisherEntity = this.findById(publisher.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Publisher not found"));
        return this.save(publisherEntity.update(publisher)).toPublisher();
    }

    default void deletePublisher(String id) {
        this.deleteById(id);
    }
}
