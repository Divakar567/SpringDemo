package com.example.demo.service;

import com.example.demo.model.DataRow;

import java.util.List;

public interface BatchInsertService {
    List<DataRow> saveData(List<DataRow> data);

    List<DataRow> updateData(List<DataRow> data);
}
