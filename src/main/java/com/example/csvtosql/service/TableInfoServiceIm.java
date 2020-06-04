package com.example.csvtosql.service;

import com.example.csvtosql.entity.TableInfoEntity;
import com.example.csvtosql.entity.TableInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TableInfoServiceIm implements TableInfoService{

    private static String url;
    private static String userName;
    private static String password;
    private static String driverName;
    private static Connection con;
    private static Environment env;
    private static TableInfoRepository repository;


//-------------------------------
//  사용자의 table(CSV to MySQL) 정보
//-------------------------------

    //기본 생성자(Default Constructor)
    public TableInfoServiceIm() {
    }


    @Autowired
    public TableInfoServiceIm(
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
        this.repository = repository;
        this.env = env;
        connect();

    }

    //MySQL DB Connect
    @Override
    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName(this.driverName);
        con = DriverManager.getConnection(url, userName, password);
    }

    //TableInfo 속성 추가
    @Override
    public TableInfoEntity addTableInfoData(String userId, String tableName){
        String tableUrl = "localhost:" + this.env.getProperty("local.server.port") + "/" + tableName;
        TableInfoEntity tableInfoEntity = new TableInfoEntity();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todate = formatter.format(new Date());
        tableInfoEntity.setUserId(userId);
        tableInfoEntity.setTableName(tableName);
        tableInfoEntity.setUrl(tableUrl);
        tableInfoEntity.setCreateTableTime(todate);

        return repository.save(tableInfoEntity);
    }

}
