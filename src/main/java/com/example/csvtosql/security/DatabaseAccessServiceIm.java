package com.example.csvtosql.security;

import com.example.csvtosql.entity.KeyInfoEntity;
import com.example.csvtosql.entity.KeyInfoRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseAccessServiceIm implements DatabaseAccessService {

    private static String url;
    private static String userName;
    private static String password;
    private static String driverName;
    private static Connection con;
    private static Environment env;
    private static KeyInfoRepository repository;
    private static ResultSet rs;
    private static Statement stmt;


//-------------------------------
//     데이터 접근 권한 설정(UUID)
//-------------------------------

    //기본 생성자(Default Constructor)
    public DatabaseAccessServiceIm() {
    }


    @Autowired
    public DatabaseAccessServiceIm(
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

    //사용자가 입력한 Key 값이 DB에 저장되어 있는 Key값과 다를 경우 데이터 조회 불가
    @Override
    public String compareUuid(String userUuid) {

        String uuid = repository.findById((long) 1).get().getUuid();

        if(userUuid.equals(uuid)) {
            return "1";
        } else
            return "";
    }

}
