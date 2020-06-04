package com.example.csvtosql.service;

import com.example.csvtosql.repository.CsvToSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Scanner;

@Service
public class FileReadServiceIm implements FileReadService {

    CsvToSqlRepository db;

    private Path root = Paths.get("C:\\shared");
    private static String userId=null;


//--------------------------------------
//    파일 경로 동적으로 읽은 후 table 생성
//--------------------------------------


    @Autowired
    public FileReadServiceIm(CsvToSqlRepository db) {
        this.db = db;
    }


    @Override
    public void filePath(String user) throws SQLException, FileNotFoundException {

        //파일 경로 지정
        String path = new StringBuilder().append(root).append("\\").append(user).toString();
        userId = user.toString();

        String fileName = null;
        File dir = new File(path);
        File[] fileList = dir.listFiles();

        //지정한 경로 내 파일 이름 읽기
        for (File fname : fileList) {
            if (fname.isFile()) {
                fileName = fname.getName();
                String fileFullName = path + "\\"+ fileName;
                dbConnect(fileName,fileFullName);
            }
        }

        System.out.println("Upload Data Successful");
    }



    @Override
    public void dbConnect(String tName, String fileFullName) throws SQLException, FileNotFoundException {
        //MySQL connect
        CsvToSqlRepository db = new CsvToSqlRepository();

        //CSV Reader
        Scanner inputReader = new Scanner(new File(fileFullName), "utf-8");


        //CSV Columns 조회
        String columns = (inputReader.nextLine()).replace(" ", ",");

        //csv 확장자 제거
        String tableName = tName.substring(0, tName.length() - 4);

        //fileName, file Columns 값을 받아서 Table 생성
        db.createTable(tableName, columns);


        //CSV 속성 값들 SQL DB Table에 추가하기
        while (inputReader.hasNextLine()) {
            db.addData(tableName, columns, gernerateRow(inputReader.nextLine()));
        }


        //TableInfo 속성 값 추가
        TableInfoServiceIm tidb = new TableInfoServiceIm();
        tidb.addTableInfoData(userId, tableName);
    }


    //Generate suitable row for entering SQL Query
    public static String gernerateRow(String row) {
        String rowForSQL = "";
        String[] cols = row.split(",");
        for (int i = 0; i < cols.length; i++) {
            rowForSQL += "'" + cols[i] + "'" + (i != (cols.length - 1) ? "," : "");
        }
        return rowForSQL;
    }

}