package com.demo.batch.writer;

import com.demo.batch.domain.entity.TweetEntity;
import com.demo.batch.domain.repository.TweetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class TweetWriter implements ItemWriter<List<TweetEntity>> {
    private final TweetRepository tweetRepository;

    @Override
    public void write(List<? extends List<TweetEntity>> tweetChunks) {
        if(CollectionUtils.isEmpty(tweetChunks)) {
            return;
        }
        tweetChunks.forEach(tweetRepository::saveAll);
        log.info("Tweets saved: {}", tweetChunks.stream().mapToInt(chunk -> chunk.size()).sum());
    }
}
