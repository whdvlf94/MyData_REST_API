package com.example.csvtosql.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface FileReadService {

    void filePath(String user) throws SQLException, FileNotFoundException;

    void dbConnect(String tableName,String fileFullName) throws SQLException, FileNotFoundException;

}
