package com.demo.batch.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Document(indexName = "#{@tweetsIndex}_#{T(java.time.LocalDate).now().toString()}")
public class TweetEntity implements Serializable {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String text;
    @Field(type = FieldType.Keyword)
    private String lang;
    @Field(type = FieldType.Integer)
    private Integer likeCount;
    @Field(type = FieldType.Integer)
    private Integer retweetCount;
    @Field(type = FieldType.Integer)
    private Integer replyCount;
    @Field(type = FieldType.Date)
    private LocalDateTime createdDate;
    @Field(type = FieldType.Keyword)
    private String createdBy;
}
