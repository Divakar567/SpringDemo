package com.demo.batch.config;

import com.demo.batch.domain.entity.TweetEntity;
import com.demo.batch.listener.TweetJobExecutionDecider;
import com.demo.batch.listener.TweetJobExecutionListener;
import com.demo.batch.listener.TweetReadListener;
import com.demo.batch.processor.TweetProcessor;
import com.demo.batch.reader.TweetReader;
import com.demo.batch.writer.TweetWriter;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@Import(DataSourceAutoConfiguration.class)
public class BatchJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job tweetProcessingJob(Step tweetProcessingStep,
                                  TweetJobExecutionListener jobExecutionListener,
                                  TweetJobExecutionDecider jobExecutionDecider) {
        return jobBuilderFactory.get(BatchJob.TWEET_PROCESSING_JOB.name())
                .start(tweetProcessingStep)
                .listener(jobExecutionListener)
                .next(jobExecutionDecider)
                .build()
                .build();
    }

    @Bean
    protected Step tweetProcessingStep(TweetReader tweetReader,
                                       TweetProcessor tweetProcessor,
                                       TweetWriter tweetWriter,
                                       TweetReadListener tweetReadListener) {
        return stepBuilderFactory.get(JobStep.TWEET_PROCESSING_STEP.name())
                .<TweetList, List<TweetEntity>> chunk(1)
                .reader(tweetReader)
                .processor(tweetProcessor)
                .writer(tweetWriter)
                .listener(tweetReadListener)
                .build();
    }

    public enum BatchJob {
        TWEET_PROCESSING_JOB
    }

    public enum JobStep {
        TWEET_PROCESSING_STEP
    }
}
