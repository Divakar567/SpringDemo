package com.demo.batch.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.TweetList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TweetReader implements ItemReader<TweetList> {
    private final TwitterClient twitterClient;
    private final WebClient twitterWebClient;
    private final ObjectMapper objectMapper;
    @Value("${twitter.credentials.bearerToken}")
    private String bearerToken;

    private String query = "money";
    private String tweetFields = "author_id,created_at,entities,geo,in_reply_to_user_id,lang,possibly_sensitive,referenced_tweets,source";
    private Integer maxResults = 10;
    @Setter @Getter
    private String nextToken;

    @Override
    public TweetList read() {
        StringBuilder url = new StringBuilder("/2/tweets/search/recent?");
        url.append("query=").append(query).append("&");
        url.append("tweet.fields=").append(tweetFields).append("&");
        url.append("max_results=").append(maxResults).append("&");
        if (!StringUtils.isEmpty(nextToken)) {
            url.append("next_token=").append(nextToken).append("&");
        }
        Mono<TweetList> tweetList = twitterWebClient.get().uri(url.toString())
                .header("Authorization", "Bearer " + bearerToken)
                .retrieve()
                .bodyToMono(TweetList.class);
        return tweetList.block();
    }
}
