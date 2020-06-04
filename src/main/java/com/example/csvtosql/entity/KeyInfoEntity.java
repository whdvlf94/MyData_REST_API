package com.example.csvtosql.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "keyInfo")
public class KeyInfoEntity {

    //nullable = false 추가할 것
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;

    @Column
    private String uuid;

}
