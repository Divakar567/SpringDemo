package com.demo.batch.listener;

import com.demo.batch.config.BatchJobConfiguration;
import com.demo.batch.reader.TweetReader;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class TweetReadListener implements ItemReadListener<TweetList> {
    private final TweetReader tweetReader;
    private final JobOperator jobOperator;

    @SneakyThrows
    @Override
    public void beforeRead() {
        Set<Long> runningExecutions = jobOperator.getRunningExecutions(BatchJobConfiguration.BatchJob.TWEET_PROCESSING_JOB.name());
        for(Long executionId : runningExecutions) {
            if(executionId == 3) {
                jobOperator.abandon(3);
                return;
            }
        }
        log.info("Before read tweets token: {}", tweetReader.getNextToken());
    }

    @Override
    public void afterRead(TweetList item) {
        tweetReader.setNextToken(item.getMeta().getNextToken());
    }

    @Override
    public void onReadError(Exception ex) {
        log.error("Exception while reading the tweets: ", ex);
    }
}
