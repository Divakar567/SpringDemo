package com.example.library.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;

import java.util.Arrays;

@Getter
@Builder
public class SearchEvent {
    private final String topic;
    private final Integer partition;
    private final Object payload;
    private final Operation operation;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public enum Operation {
        SAVE, DELETE
    }

    @SneakyThrows
    public ProducerRecord<String, String> toRecord() {
        Iterable<Header> headers = Arrays.asList(new Header() {
            @Override
            public String key() {
                return "operation";
            }

            @Override
            public byte[] value() {
                return operation.name().getBytes();
            }
        });
        return new ProducerRecord<>(topic, null, null, null,objectMapper.writeValueAsString(payload), headers);
    }
}
