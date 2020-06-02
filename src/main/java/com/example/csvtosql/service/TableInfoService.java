package com.example.csvtosql.service;

import com.example.csvtosql.entity.TableInfo;
import com.example.csvtosql.entity.TableInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Date;

@Service
public class TableInfoService {

    private static String url;
    private static String userName;
    private static String password;
    private static String driverName;
    private static Connection con;
    private static Environment env;
    private static TableInfoRepository repository;

//    private static ResultSet rs;
//    private static Statement stmt;


    //기본 생성자(Default Constructor)
    public TableInfoService() {
    }



    @Autowired
    public TableInfoService(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String userName,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name}") String driverName,
            TableInfoRepository repository,
            Environment env) throws SQLException, ClassNotFoundException {

        this.url= url;
        this.userName = userName;
        this.password = password;
        this.driverName = driverName;
//        this.port = port;
        this.repository = repository;
        this.env = env;
        connect();

    }

    //MySQL DB Connect
    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName(this.driverName);
        con = DriverManager.getConnection(url, userName, password);
    }

    //TableInfo 속성 추가
    public TableInfo addTableInfoDatas(String userId, String tableName) throws SQLException {
        String tableUrl = "localhost:" + this.env.getProperty("local.server.port") + "/" + tableName;
        TableInfo tableInfo = new TableInfo();
        Date date = new Date();
        tableInfo.setUserId(userId);
        tableInfo.setTableName(tableName);
        tableInfo.setUrl(tableUrl);
        tableInfo.setCreateTableTime(date+"");

        return repository.save(tableInfo);
    }


        //TableInfo 속성 추가
//    public void addTableInfoData(String userId, String tableName) throws SQLException {
//        String tableUrl = "localhost:" + this.port + "/" + tableName;
//        TableInfo tableInfo = new TableInfo();
//        Date date = new Date();
//        tableInfo.setUserId(userId);
//        tableInfo.setTableName(tableName);
//        tableInfo.setUrl(tableUrl);
//        tableInfo.setCreateTableTime(date+"");
//
//        //JDBC를 이용하여 속성값 추가하기
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("insert into table_info (create_table_time, table_name, url, user_id) ");
//        stringBuilder.append(String.format("values('%s', '%s', '%s', '%s')",
//            tableInfo.getCreateTableTime(),
//            tableInfo.getTableName(),
//            tableInfo.getUrl(),
//            tableInfo.getUserId()
//        ));
//
//
//        stmt = (Statement) con.createStatement();
//        stmt.executeUpdate(stringBuilder.toString());
//    }
}
