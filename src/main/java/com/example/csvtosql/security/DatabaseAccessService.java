package com.example.csvtosql.security;

import java.sql.SQLException;

public interface DatabaseAccessService {

    void connect() throws ClassNotFoundException, SQLException;

    String compareUuid(String userUuid);
}
