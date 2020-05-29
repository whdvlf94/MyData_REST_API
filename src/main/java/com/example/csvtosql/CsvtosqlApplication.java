package com.example.csvtosql;

import com.example.csvtosql.repository.DataRepository;
import com.example.csvtosql.service.CSV2JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
public class CsvtosqlApplication {

    private static String fileName = "Z:\\examples.csv";
//    private static String fileName = "example.csv";


    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException, URISyntaxException {
//        SpringApplication.run(CsvtosqlApplication.class, args);


        //테이블 이름
        String tableName = fileName.substring(3, fileName.length() - 4);


        //CSV 파일 로컬 PC에 JSON 파일 생성해주는 Service
//        CSV2JSON csv2json = new CSV2JSON(fileName);


        //MySQL connect
        DataRepository db = new DataRepository();

        //CSV Reader
        Scanner inputReader = new Scanner(new File(fileName),"UTF-8");


        //CSV Columns 조회
        String columns = (inputReader.nextLine()).replace(" ", ",");



        //fileName, file Columns 값을 받아서 Table 생성
        db.createTable(tableName, columns);


        //Header 값만 들어있는 테이블 생성
//        String headerTableName = tableName + "header";
//        db.createHeaderTable(headerTableName);

        //CSV 속성 값들 SQL DB Table에 추가하기
        while (inputReader.hasNextLine()) {
            db.addData(tableName, columns, gernerateRow(inputReader.nextLine()));
        }
//        db.addHeaderData(headerTableName, columns);

        SpringApplication.run(CsvtosqlApplication.class, args);
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
