package com.example.demo.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Data")
@Table(name = "data")
public class DataEntity {

    @Id
    private String id;

    @Column(name = "attribute", nullable = false)
    private String attribute;

    @Column(name = "attribute_value", nullable = false)
    private String attributeValue;
}
