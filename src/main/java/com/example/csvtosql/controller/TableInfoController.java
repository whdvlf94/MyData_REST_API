package com.example.csvtosql.controller;

import com.example.csvtosql.entity.TableInfo;
import com.example.csvtosql.entity.TableInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TableInfoController {

    @Autowired
    private TableInfoRepository repository;

    //테이블 정보 조회
    @GetMapping(value = "{userid}/tableinfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TableInfo> getTableInfo(@PathVariable String userid) {
        return repository.findByUserId(userid);
    }

}
