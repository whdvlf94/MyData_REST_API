package com.example.csvtosql.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tableInfo")
public class TableInfo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 50)
    private String tableName;

    @Column(nullable = false, length = 50)
    private Date CreateTableTime;
}
