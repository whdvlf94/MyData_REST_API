package com.example.csvtosql.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeyInfoRepository extends JpaRepository<KeyInfoEntity, String> {
    List<KeyInfoEntity> findByUserId(String userId);

}
