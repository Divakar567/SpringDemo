package com.example.library.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* save*(..))")
    public void whenSavingEntities(){};

    @Pointcut("execution(* update*(..))")
    public void whenUpdatingEntities(){};

    @Pointcut("execution(* delete*(..))")
    public void whenDeletingEntities(){};

    @Pointcut("target(org.springframework.data.jpa.repository.JpaRepository)")
    public void atJpaRepositories(){}

    @AfterReturning(value = "atJpaRepositories() && (whenSavingEntities() || whenUpdatingEntities() || whenDeletingEntities())", returning = "entity")
    public void logReturnedData(JoinPoint jp, Object entity) {
        log.info("Saved entity {}: {}", jp.getSignature(), entity);
    }

}
