package com.example.library.util;

import com.example.library.model.SearchEvent;
import com.example.library.util.annotation.Searchable;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class SearchAuditAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public SearchAuditAspect(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Pointcut("@annotation(searchable)")
    public void isSearchable(Searchable searchable){
        /*
          This pointcut will match all the methods with @Searchable annotation
         */
    }

    @AfterReturning(value = "isSearchable(searchable)", returning = "doc", argNames = "jp,searchable,doc")
    public void sendSearchEntityToKafka(JoinPoint jp, Searchable searchable, Object doc) {
        log.info("Method signature: {}", jp.getSignature());
        try {
            kafkaTemplate.send(
                    SearchEvent
                            .builder()
                            .topic(searchable.value())
                            .payload(doc)
                            .operation(jp.getSignature().getName().toLowerCase().contains("delete")?
                                    SearchEvent.Operation.DELETE
                                    :SearchEvent.Operation.SAVE)
                            .build().toRecord()
            );
        } catch (Exception e) {
            log.error("Exception while converting object to json: ", e);
        }
    }
}
