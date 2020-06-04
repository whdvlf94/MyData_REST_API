package com.example.csvtosql.repository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Map;

@Repository
public class SqlToJsonRepository {
    private static String url;
    private static String userName;
    private static String password;
    private static String driverName;

    private static Connection con;
    private static ResultSet rs;
    private static Statement stmt;

//-------------------------------
//      JSON 형식으로 데이터 호출
//-------------------------------
    @Autowired
    public SqlToJsonRepository(
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


    //테이블 전체 조회
    public JSONArray findAll(String tableName) throws SQLException, ClassNotFoundException, JSONException {
        JSONArray jsonArray = new JSONArray();

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
