package com.example.demo.controller;

import com.example.demo.model.DataRow;
import com.example.demo.service.BatchInsertService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class BatchInsertController {

    private final BatchInsertService batchInsertService;

    @PostMapping("/insert")
    public List<DataRow> batchInsertData(@NotEmpty @RequestBody List<DataRow> data) {
        return batchInsertService.saveData(data);
    }

    @PutMapping("/update")
    public List<DataRow> batchUpdateData(@NotEmpty @RequestBody List<DataRow> data) {
        return batchInsertService.updateData(data);
    }
}
