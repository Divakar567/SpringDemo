package com.example.demo.service;

import com.example.demo.model.DataRow;
import com.example.demo.repository.DataRepository;
import com.example.demo.repository.entity.DataEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchInsertServiceImpl implements BatchInsertService {
    private final DataRepository dataRepository;
    @Override
    public List<DataRow> saveData(List<DataRow> data) {
        List<DataEntity> dataEntities = data.stream().map(this::convert).collect(Collectors.toList());
        dataEntities = dataRepository.saveAll(dataEntities);
        return dataEntities.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public List<DataRow> updateData(List<DataRow> data) {
        List<DataEntity> dataEntities = data.stream()
                .map(row -> {
                    Optional<DataEntity> entityOptional = dataRepository.findByAttribute(row.getAttribute());
                    if (entityOptional.isPresent()) {
                        entityOptional.get().setAttributeValue(row.getAttributeValue());
                        return entityOptional.get();
                    }
                    return this.convert(row);
                }).collect(Collectors.toList());
        CompletableFuture.runAsync(() -> dataRepository.saveAll(dataEntities));
        return dataEntities.stream().map(this::convert).collect(Collectors.toList());
    }

    private DataEntity convert(DataRow data) {
        return DataEntity.builder()
                .id(UUID.randomUUID().toString())
                .attribute(data.getAttribute())
                .attributeValue(data.getAttributeValue())
                .build();
    }

    private DataRow convert(DataEntity entity) {
        return DataRow.builder()
                .id(entity.getId())
                .attribute(entity.getAttribute())
                .attributeValue(entity.getAttributeValue())
                .build();
    }
}
