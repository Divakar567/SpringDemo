package com.demo.batch.processor;

import com.demo.batch.domain.entity.TweetEntity;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TweetProcessor implements ItemProcessor<TweetList, List<TweetEntity>> {
    @Override
    public List<TweetEntity> process(TweetList tweetList) {
        return tweetList.getData().stream().map(tweet -> {
            TweetEntity entity = new TweetEntity();
            entity.setText(tweet.getText());
            entity.setLang(tweet.getLang());
            entity.setLikeCount(tweet.getPublicMetrics() == null? 0 : tweet.getLikeCount());
            entity.setReplyCount(tweet.getPublicMetrics() == null? 0 : tweet.getReplyCount());
            entity.setRetweetCount(tweet.getPublicMetrics() == null? 0 : tweet.getRetweetCount());
            entity.setCreatedBy(tweet.getAuthorId());
            entity.setCreatedDate(tweet.getCreatedAt());
            log.info("Tweet processed {}: {}", entity.getCreatedBy(), entity.getText());
            return entity;
        }).collect(Collectors.toList());
    }
}
