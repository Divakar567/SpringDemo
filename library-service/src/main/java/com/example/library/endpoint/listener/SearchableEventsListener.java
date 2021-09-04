package com.example.library.endpoint.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SearchableEventsListener {

    private final ObjectMapper objectMapper;
    SearchableEventsListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topicPattern = "es-*-topic")
    public void processSearchableEntity(List<String> searchableEntities) {
        searchableEntities.forEach(searchableEntity -> {
            log.info("Searchable entity received: {}", searchableEntity);
        });
    }
}
