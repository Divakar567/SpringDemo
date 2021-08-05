package com.example.library.service;

import com.example.library.model.Publisher;

import java.util.List;

public interface PublisherService {
    List<Publisher> getPublishers();

    Publisher getPublisher(String id);

    Publisher savePublisher(Publisher publisher);

    Publisher updatePublisher(Publisher publisher);

    Publisher deletePublisher(String id);
}
