# Read Me
> API Server Document



#### ※ URL Setting

```
-------- 사용자 접근 권한 설정(UUID 사용) ----------
[데이터 전체 조회]
localhost:8080/{tablename}?key={사용자 Key Value}
예)
localhost:8080/example?key=xyz

[데이터 특정 칼럼 값 조회]
localhost:8080/{tablename}/detail?key={사용자 Key Value}&{테이블 헤더 값}={value}&...
예)
localhost:8080/example/detail?key=xyz&관할경찰서=파주경찰서

-------- 사용자 접근 권한 설정 X(UUID 사용 안함) ----------
[사용자 별 table 정보 조회]
localhost:8080/{userid}/tableinfo
예)
localhost:8080/user1/tableinfo

-------- Frontend 와의 연동 ----------
[POST 방식을 통해 userid, UUID 정보 저장]
localhost:8080/start?user={userid}&userKey={userkey}
예)
localhost:8080/start?user=user1&userKey=xyz

사용자의 id, UUID를 Front의 Session으로 부터 할당받는다.
```





### 1. 주요 기능



#### 1. 사용자가 업로드한 CSV 파일을 MySQL DB에 삽입 Service

- `repository/CsvToSqlRepository`



#### 2. DB Table 호출 시 JSON Type으로 출력하는 Service

- `repository/SqlToJsonRepository`



#### 3. Key 값을 통한 데이터 접근 권한 설정 Service

- `security/DatabaseAccessServiceIm`





### 2. 환경 설정



1. 사용자 별 Key 정보, 생성된 Table list 정보는 JPA 방식 사용
2. 사용자가 업로드한 CSV file을 DB table에 삽입하기 위해서는 동적인 처리 과정이 필요하므로 JDBC 사용
3. Front로 부터 POST 요청(Session 값 전달)이 들어을 때, Service(API Server) 가 가동된다.
