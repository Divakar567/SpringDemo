package com.demo.batch.controller;

import com.demo.batch.domain.entity.TweetEntity;
import com.demo.batch.domain.repository.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
public class TweetController {
    private final TweetRepository tweetRepository;

    @GetMapping("/tweets")
    public ResponseEntity<Flux<TweetEntity>> getTweets() {
        return ResponseEntity.ok(Flux.fromIterable(tweetRepository.findAll()));
    }
}
