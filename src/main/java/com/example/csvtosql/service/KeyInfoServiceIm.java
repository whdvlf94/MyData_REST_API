package com.example.csvtosql.service;

import com.example.csvtosql.entity.KeyInfoEntity;
import com.example.csvtosql.entity.KeyInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Service
public class KeyInfoServiceIm implements KeyInfoService {

    private static String url;
    private static String userName;
    private static String password;
    private static String driverName;
    private static Connection con;
    private static Environment env;
    private static KeyInfoRepository repository;


//-------------------------------
//  사용자 Key(UUID) 정보 table
//-------------------------------


    //기본 생성자(Default Constructor)
    public KeyInfoServiceIm() {
    }


    @Autowired
    public KeyInfoServiceIm(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String userName,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name}") String driverName,
            KeyInfoRepository repository,
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
    public KeyInfoEntity addKeyInfoData(String userId, String userKey){
        KeyInfoEntity keyInfoEntity = new KeyInfoEntity();
        keyInfoEntity.setUserId(userId);
        keyInfoEntity.setUuid(userKey);

        return repository.save(keyInfoEntity);
    }
}
