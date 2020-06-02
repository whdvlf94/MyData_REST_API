package com.example.csvtosql;

import com.example.csvtosql.repository.DataRepository;
import com.example.csvtosql.service.TableInfoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
public class CsvtosqlApplication {

    private static String fileFullName = "Z:\\userid_abc.csv";
    private static String fileName = "userid_abc.csv";
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, URISyntaxException {
        
        SpringApplication.run(CsvtosqlApplication.class, args);


        //파일 경로 지정
//        String fileName = null;
//        String path = "Z:\\";
//        File dir = new File(path);
//        File[] fileList = dir.listFiles();
//
//        //지정한 경로 내 파일 이름 읽기
//        for (File file: fileList) {
//            if(file.isFile()) {
//                fileName = file.getName();
//            }
//        }
//
//        String fileFullName = path + fileName;
//
//        System.out.println(fileName);

        //테이블 이름
        String tableName = fileName.substring(0, fileName.length() - 4);


        //CSV 파일 로컬 PC에 JSON 파일 생성해주는 Service
//        CSV2JSON csv2json = new CSV2JSON(fileName);


        //MySQL connect
        DataRepository db = new DataRepository();

        //CSV Reader
        Scanner inputReader = new Scanner(new File(fileFullName),"utf-8");



        //CSV Columns 조회
        String columns = (inputReader.nextLine()).replace(" ", ",");
//        String columns = (readAll.nextLine());




        //fileName, file Columns 값을 받아서 Table 생성
        db.createTable(tableName, columns);




        //CSV 속성 값들 SQL DB Table에 추가하기
        while (inputReader.hasNextLine()) {
            db.addData(tableName, columns, gernerateRow(inputReader.nextLine()));
        }
//        db.addHeaderData(headerTableName, columns);

//        SpringApplication.run(CsvtosqlApplication.class, args);


        //TableInfo 속성 값 추가
        String userId = "user1";
        TableInfoService idb = new TableInfoService();
        idb.addTableInfoData(userId,tableName);

//        idb.addTableInfoData(userId,tableName);
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
