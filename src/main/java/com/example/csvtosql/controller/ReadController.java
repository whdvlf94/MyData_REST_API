package com.example.csvtosql.controller;

import com.example.csvtosql.entity.KeyInfoRepository;
import com.example.csvtosql.service.FileReadService;
import com.example.csvtosql.service.KeyInfoService;
import com.example.csvtosql.service.KeyInfoServiceIm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.sql.SQLException;

@RestController
@CrossOrigin("http://localhost:3000")
public class ReadController {

    @Autowired
    private FileReadService fileReadService;

    @Autowired
    private KeyInfoService keyInfoService;

    @Autowired
    private KeyInfoRepository keyInfoRepository;

    //Front로 부터 userID , UUID 값 POST 방식으로 제공 받음
    @PostMapping("/start")
    public void readFilePath(@RequestParam("user") String user , @RequestParam("userKey") String userkey) throws FileNotFoundException, SQLException {
        fileReadService.filePath(user);


        if (keyInfoRepository.findAll().size() == 0) {
        keyInfoService.addKeyInfoData(user,userkey);}

    }


}