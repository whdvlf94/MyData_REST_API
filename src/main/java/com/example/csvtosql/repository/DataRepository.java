package com.example.csvtosql.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Map;
import java.util.Scanner;

@Configuration
public class DataRepository {

    private String port = "3306";
    private String dbName = "csv2sql";
    private String userName = "root";
    private String password = "spring";
//    @Value("${spring.datasource.url}")
//    private String url;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;

    private Connection con;
    private ResultSet rs;
    private Statement stmt;

    //기본 생성자(Default Constructor)
    public DataRepository() throws SQLException, ClassNotFoundException {
        connect();
    }

    //Overloading Constructor
    public DataRepository(String port,String dbName,String userName, String password) throws ClassNotFoundException, SQLException {
        this.port = port;
        this.dbName= dbName;
        this.userName = userName;
        this.password = password;
        connect();
    }


    //MySQL DB Connect
    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + dbName + "?serverTimezone=Asia/Seoul", userName, password);
//        con = DriverManager.getConnection(url, username, password);
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

    //헤더 테이블 생성
    public void createHeaderTable(String headerTableName) throws SQLException {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + headerTableName + " (ID int NOT NULL AUTO_INCREMENT, Header VARCHAR(255) NOT NULL ,";
        sqlCreate += "PRIMARY KEY (ID));";
        Statement stmt = con.createStatement();
        stmt.execute(sqlCreate);

    }



    //테이블 속성 추가
    public void addData(String tableName, String columns, String values) throws SQLException {
        String query = "insert into " + tableName + "(ID," + columns + ")";
        query += "values(NULL," + values + ");";


        Statement stm = (Statement) con.createStatement();
        int executeUpdate = stm.executeUpdate(query);

        System.out.println("Success Add Data");
    }

    //헤더 테이블 속성 추가
    public void addHeaderData(String tableName, String columns) throws SQLException {
        String[] cols = columns.split(",");


        for (int i = 0; i < cols.length; i++) {
            String item = cols[i];
            String query = "INSERT INTO " + tableName + "(ID,Header)" + " VALUES (NULL," + '"' + item + '"' + ");";
            Statement stm = (Statement) con.createStatement();
            stm.executeUpdate(query);
        }
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


//    ----------------------------------------REST API Controller-----------------------------------------------------


    //테이블 전체 조회
    public JSONArray findAll(String tableName) throws SQLException, ClassNotFoundException, JSONException {
        JSONArray jsonArray = new JSONArray();

        Class.forName("com.mysql.cj.jdbc.Driver");
//        con = DriverManager.getConnection(url, username, password);
        con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + dbName + "?serverTimezone=Asia/Seoul", userName, password);

        stmt = con.createStatement();

        String sql = "SELECT * FROM ";
        sql += tableName;
        rs = stmt.executeQuery(sql);
        Integer i = 0;
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        while (rs.next()){
            JSONObject jsonObject = new JSONObject();
            for (int ii = 1; ii < columnCount + 1; ii++) {
                String alias = rsmd.getColumnLabel(ii);
                jsonObject.put(alias, rs.getObject(alias));
            }
            jsonArray.put(i, jsonObject);
            i++;
        }

        return jsonArray;
    }

    //테이블 특정 파라미터 값 조회
    public JSONArray findByColNum(String tableName, Map<String, Object> queryMap) throws SQLException, ClassNotFoundException, JSONException {
        JSONArray jsonArray = new JSONArray();

        Class.forName("com.mysql.cj.jdbc.Driver");
//        con = DriverManager.getConnection(url, username, password);
        con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + dbName + "?serverTimezone=Asia/Seoul", userName, password);
        stmt = con.createStatement();

        Integer i = 0;

        String sql = "SELECT *";
        sql += " FROM "+tableName;
        sql += " where ";
        int j = 0;
        for(String mapkey : queryMap.keySet()){
            sql += mapkey + " = " +  '"' + queryMap.get(mapkey).toString() + '"';
            j ++;
            if (queryMap.size() != j) {
                sql += " AND ";
                continue;
            }
        }
        rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmdAll = rs.getMetaData();
        int columnCountAll = rsmdAll.getColumnCount();

        jsonArray = new JSONArray();
        while (rs.next()){
            JSONObject jsonObject = new JSONObject();
            for (int ii = 1; ii < columnCountAll + 1; ii++) {
                String alias = rsmdAll.getColumnLabel(ii);
                jsonObject.put(alias, rs.getObject(alias));
            }
            jsonArray.put(i, jsonObject);
            i++;
        }
        return jsonArray;
    }


}
