package com.example.library.controller;

import com.example.library.model.Publisher;
import com.example.library.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getPublishers() {
        List<Publisher> publishers = publisherService.getPublishers();
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable String id) {
        Publisher publisher = publisherService.getPublisher(id);
        return ResponseEntity.ok(publisher);
    }

    @PostMapping
    public ResponseEntity<Publisher> savePublisher(@RequestBody Publisher publisher) {
        publisher = publisherService.savePublisher(publisher);
        return ResponseEntity.ok(publisher);
    }

    @PutMapping
    public ResponseEntity<Publisher> updatePublisher(@RequestBody Publisher publisher) {
        publisher = publisherService.updatePublisher(publisher);
        return ResponseEntity.ok(publisher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Publisher> deletePublisher(@PathVariable String id) {
        Publisher publisher = publisherService.deletePublisher(id);
        return ResponseEntity.ok(publisher);
    }
}
