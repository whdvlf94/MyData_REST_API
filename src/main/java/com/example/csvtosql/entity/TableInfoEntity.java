package com.example.csvtosql.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tableInfo")
public class TableInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column
    private String userId;

    @Column(nullable = false, length = 50)
    private String tableName;

    @Column(nullable = false, length = 150)
    private String url;

    @Column(nullable = false, length = 50)
    private String createTableTime;

}
