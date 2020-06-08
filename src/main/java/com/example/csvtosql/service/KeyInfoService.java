package com.example.csvtosql.service;

import java.sql.SQLException;

public interface KeyInfoService {
    void connect() throws ClassNotFoundException, SQLException;

    Object addKeyInfoData(String userId, String userKey);
}
