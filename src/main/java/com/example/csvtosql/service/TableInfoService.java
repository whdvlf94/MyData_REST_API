package com.example.csvtosql.service;

import com.example.csvtosql.entity.TableInfoEntity;

import java.sql.SQLException;

public interface TableInfoService {

    void connect() throws ClassNotFoundException, SQLException;

    TableInfoEntity addTableInfoData(String userId, String tableName);

}
