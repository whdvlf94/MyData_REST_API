package com.example.csvtosql.service;

import com.example.csvtosql.entity.TableInfo;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Date;

@Service
public class TableInfoService {

    private Connection con;
    private Statement stmt;


    //TableInfo 속성 추가
    public void addTableInfoData(String userId, String tableName) throws SQLException, ClassNotFoundException {
        String url = "localhost:8080/" + tableName;
        Date date = new Date();
        TableInfo tableInfo = new TableInfo();
        tableInfo.setUserId(userId);
        tableInfo.setTableName(tableName);
        tableInfo.setUrl(url);
        tableInfo.setCreateTableTime(date+"");

        //JDBC를 이용하여 속성값 추가하기
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csv2sql?serverTimezone=Asia/Seoul","root","spring");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("insert into table_info (create_table_time, table_name, url, user_id) ");
        stringBuilder.append(String.format("values('%s', '%s', '%s', '%s')",
            tableInfo.getCreateTableTime(),
            tableInfo.getTableName(),
            tableInfo.getUrl(),
            tableInfo.getUserId()
        ));


        Statement stmt = (Statement) con.createStatement();
        int executeUpdate = stmt.executeUpdate(stringBuilder.toString());
    }
}
