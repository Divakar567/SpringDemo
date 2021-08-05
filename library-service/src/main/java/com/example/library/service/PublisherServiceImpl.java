package com.example.library.service;

import com.example.library.model.Publisher;
import com.example.library.repository.PublisherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<Publisher> getPublishers() {
        return publisherRepository.getPublishers();
    }

    @Override
    public Publisher getPublisher(String id) {
        return publisherRepository.getPublisher(id);
    }

    @Override
    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.savePublisher(publisher);
    }

    @Override
    public Publisher updatePublisher(Publisher publisher) {
        if (publisher.getId() == null || "".equals(publisher.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Publisher id is required");
        }
        return publisherRepository.updatePublisher(publisher);
    }

    @Override
    public Publisher deletePublisher(String id) {
        Publisher publisher = this.getPublisher(id);
        if (publisher != null) {
            publisherRepository.deletePublisher(id);
        }
        return publisher;
    }
}
