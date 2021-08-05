package com.demo.batch.domain.repository;

import com.demo.batch.domain.entity.TweetEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TweetRepository extends ElasticsearchRepository<TweetEntity, String> {

}
