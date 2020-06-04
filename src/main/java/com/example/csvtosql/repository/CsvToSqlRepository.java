package com.example.csvtosql.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Scanner;

@Repository
public class CsvToSqlRepository {

    private static String url;
    private static String userName;
    private static String password;
    private static String driverName;

    private static Connection con;
    private static ResultSet rs;
    private static Statement stmt;

//-------------------------------
//      CSV file Table 저장
//-------------------------------

    public CsvToSqlRepository() {
    }


    @Autowired
    public CsvToSqlRepository(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String userName,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name}") String driverName) throws SQLException, ClassNotFoundException {

        this.url= url;
        this.userName = userName;
        this.password = password;
        this.driverName = driverName;
        connect();
    }



    //MySQL DB Connect
    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName(this.driverName);
        con = DriverManager.getConnection(url, userName, password);
    }


    //테이블 생성
    public void createTable(String tableName, String columns) throws SQLException {

        String[] cols = columns.split(",");
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + tableName + " (ID int NOT NULL AUTO_INCREMENT,";
        for (int i = 0; i < cols.length; i++) {
            sqlCreate += cols[i] + checkType(cols[i]) + ",";
        }
        sqlCreate += "PRIMARY KEY (ID))";
        Statement stmt = con.createStatement();
        stmt.execute(sqlCreate);
    }


    //테이블 속성 추가
    public void addData(String tableName, String columns, String values) throws SQLException {
        String query = "insert into " + tableName + "(ID," + columns + ")";
        query += "values(NULL," + values + ");";


        Statement stm = (Statement) con.createStatement();
        int executeUpdate = stm.executeUpdate(query);

//        System.out.println("Success Add Data");
    }


    //데이터 타입 설정
    public String checkType(String columnName) {
        Scanner sc = new Scanner(columnName);
        if (sc.hasNextInt()) {
            return " INT(11)";
        } else if (sc.hasNextDouble()) {
            return " DECIMAL(8,2)";
        } else {
            return " VARCHAR(255)";
        }
    }

}
