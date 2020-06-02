package com.example.csvtosql.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableInfoRepository extends JpaRepository<TableInfo,Long> {

    List<TableInfo> findByUserId(String userid);
}
