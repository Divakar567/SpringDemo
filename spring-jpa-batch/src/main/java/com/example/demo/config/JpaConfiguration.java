package com.example.demo.config;

import com.example.demo.SpringJpaBatchApplication;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.time.Instant;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(
        auditorAwareRef = "auditorProvider",
        dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(
        entityManagerFactoryRef = "jpaEntityManagerFactory",
        transactionManagerRef = "jpaTransactionManager",
        basePackages = "com.dfx.cl.metadata.repository")
@EnableTransactionManagement
@RequiredArgsConstructor
public class JpaConfiguration {

    @Bean
    @Qualifier("jpaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource jpaDataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
                .create()
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();
        dataSource.setMinimumIdle(5);
        dataSource.setMaximumPoolSize(50);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                          @Qualifier("jpaDataSource") DataSource jpaDataSource) {
        return builder
                .dataSource(jpaDataSource)
                .packages(SpringJpaBatchApplication.class)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("ANONYMOUS");
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(Instant.now());
    }
}
