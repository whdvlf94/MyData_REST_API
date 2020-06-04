package com.example.csvtosql;

import com.example.csvtosql.service.FileReadService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;


@SpringBootApplication
@EnableJpaRepositories
public class CsvtosqlApplication {

    public static void main(String[] args) {

        SpringApplication.run(CsvtosqlApplication.class, args);

    }
}
