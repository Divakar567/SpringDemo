package com.example.library.repository;

import com.example.library.model.Publisher;

import java.util.List;

public interface PublisherRepository {
    List<Publisher> getPublishers();

    Publisher getPublisher(String id);

    Publisher savePublisher(Publisher publisher);

    Publisher updatePublisher(Publisher publisher);

    void deletePublisher(String id);
}
