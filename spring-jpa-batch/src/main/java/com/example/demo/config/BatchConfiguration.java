package com.example.demo.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;

@Getter
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BatchConfiguration {

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        TaskExecutorBuilder builder = new TaskExecutorBuilder();
        builder.threadNamePrefix("batch-exec-");
        builder.corePoolSize(Runtime.getRuntime().availableProcessors());
        builder.maxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        builder.queueCapacity(10);
        return builder.build();
    }

    @Bean
    public DefaultBatchConfigurer batchConfigurer(DataSource batchDataSource) {
        return new DefaultBatchConfigurer(batchDataSource) {
            @Override
            protected JobLauncher createJobLauncher() throws Exception {
                SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
                jobLauncher.setJobRepository(getJobRepository());
//                jobLauncher.setTaskExecutor(threadPoolTaskExecutor());
                jobLauncher.afterPropertiesSet();
                return jobLauncher;
            }
        };
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource batchDataSource(DataSourceProperties properties) {
        return DataSourceBuilder
                .create()
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
    }
}
