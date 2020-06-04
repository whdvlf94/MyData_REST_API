package com.example.csvtosql.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CSV2JSON {

//------------------------------------------------------
//  입력받은 CSV 파일을 변환하여 특정 지점에 json 파일로 제공
//------------------------------------------------------

    public CSV2JSON() {
    }

    public CSV2JSON(String filename) throws IOException {
        File input = new File(filename);
        File output = new File("Z:\\output.json");

        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();

        // Read data from CSV file
        List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();

        ObjectMapper mapper = new ObjectMapper();

        // Write JSON formated data to output.json file
        mapper.writerWithDefaultPrettyPrinter().writeValue(output, readAll);

        // Write JSON formated data to stdout
//        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll));
    }

}

