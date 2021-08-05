package com.demo.batch.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.reactive.ReactiveRestClients;
import org.springframework.data.elasticsearch.config.AbstractReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.config.EnableReactiveElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

@Data
@Configuration
@EqualsAndHashCode(callSuper = true)
@EnableReactiveElasticsearchAuditing
@EnableReactiveElasticsearchRepositories()
@ConfigurationProperties("spring.data.elasticsearch.client.reactive")
public class ElasticsearchConfiguration extends AbstractReactiveElasticsearchConfiguration {
    private String endpoints;
    private String username;
    private String password;
    private Long connectionTimeout;

    @Bean
    @Override
    public ReactiveElasticsearchClient reactiveElasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(endpoints)
                .withConnectTimeout(connectionTimeout)
                .withBasicAuth(username, password)
                .build();
        return ReactiveRestClients.create(clientConfiguration);
    }
}
