package com.example.demo.repository;

import com.example.demo.repository.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataRepository extends JpaRepository<DataEntity, String> {
    Optional<DataEntity> findByAttribute(String attribute);
}
