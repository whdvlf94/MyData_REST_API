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


//-------------------------------
//     데이터 접근 권한 설정(UUID)
//-------------------------------

    private static KeyInfoRepository repository;

    //기본 생성자(Default Constructor)
    public DatabaseAccessServiceIm() {
    }

    @Autowired
    public DatabaseAccessServiceIm(KeyInfoRepository repository) {
        this.repository = repository;
    }


    @Override
    public String compareUuid(String userUuid) {
        String uuid = repository.findById((long) 1).get().getUuid();

        if (userUuid.equals(uuid)) {
            return "1";
        } else
            return "";
    }

}
