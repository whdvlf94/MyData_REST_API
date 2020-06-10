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
import java.util.ArrayList;
import java.util.List;


@Service
public class KeyInfoServiceIm implements KeyInfoService {



//-------------------------------
//  사용자 Key(UUID) 정보 table
//-------------------------------

    private static KeyInfoRepository repository;

    //기본 생성자(Default Constructor)
    public KeyInfoServiceIm() {
    }


    @Autowired
    public KeyInfoServiceIm(KeyInfoRepository repository){
        this.repository = repository;
    }


    //key_info 속성 추가
    @Override
    public KeyInfoEntity addKeyInfoData(String userId, String userKey) {

        KeyInfoEntity keyInfoEntity = new KeyInfoEntity();

        keyInfoEntity.setUserId(userId);
        keyInfoEntity.setUuid(userKey);
        return repository.save(keyInfoEntity);
    }
}
