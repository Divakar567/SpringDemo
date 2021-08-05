package com.demo.batch.config;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@Configuration
@ConfigurationProperties("twitter.credentials")
public class TwitterConfiguration {
    private String apiKey;
    private String apiSecretKey;
    private String accessToken;
    private String accessTokenSecret;

    @Bean
    public TwitterClient twitterClient() {
        return new TwitterClient(TwitterCredentials.builder()
                .accessToken(accessToken)
                .accessTokenSecret(accessTokenSecret)
                .apiKey(apiKey)
                .apiSecretKey(apiSecretKey)
                .build());
    }

    @Bean
    public WebClient twitterWebClient() {
        return WebClient.create("https://api.twitter.com");
    }

    @Bean
    public String tweetsIndex(@Value("${twitter.index.tweets}") String index) {
        return index;
    }
}
