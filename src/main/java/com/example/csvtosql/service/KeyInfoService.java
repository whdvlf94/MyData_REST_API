package com.example.csvtosql.service;

import com.example.csvtosql.entity.KeyInfoEntity;

import java.sql.SQLException;

public interface KeyInfoService {
    void connect() throws ClassNotFoundException, SQLException;

    KeyInfoEntity addKeyInfoData(String userId, String userKey);
}
