package com.example.csvtosql.service;

import com.example.csvtosql.entity.TableInfoRepository;
import com.example.csvtosql.repository.CsvToSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class FileReadServiceIm implements FileReadService {

    CsvToSqlRepository db;
    TableInfoRepository tableInfoRepository;

//    private Path root = Paths.get("/var/lib/restapi");
    private Path root = Paths.get("C:\\shared");
    private static String userId=null;


//--------------------------------------
//    파일 경로 동적으로 읽은 후 table 생성
//--------------------------------------


    @Autowired
    public FileReadServiceIm(CsvToSqlRepository db, TableInfoRepository tableInfoRepository) {
        this.db = db;
        this.tableInfoRepository = tableInfoRepository;
    }


    @Override
    public void filePath(String user) throws SQLException, FileNotFoundException {


        //파일 경로 지정
//        String path = new StringBuilder().append(root).append("/").append(user).toString();
        String path = new StringBuilder().append(root).append("\\").append(user).toString();

//         path = C:\\shared\\user2
        userId = user.toString();

        String fileName = null;
        File dir = new File(path);
        File[] fileList = dir.listFiles();


        List<String> tableNameList = new ArrayList<>();
        for(int i=0; i<tableInfoRepository.findAll().size();i++){

            String tableName = tableInfoRepository.findByUserId(user).get(i).getTableName();
            tableNameList.add(tableName);

        }

        //지정한 경로 내 파일 이름 읽기
        for (File fname : fileList) {
            if (fname.isFile()) {
                fileName = fname.getName();
//                String fileFullName = path + "/"+ fileName;
                String fileFullName = path + "\\"+ fileName;


                String realFileName = fileName.substring(0,fileName.length()-4);

                if (!tableNameList.contains(realFileName)){
                    dbConnect(fileName,fileFullName);
                }
                continue;

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