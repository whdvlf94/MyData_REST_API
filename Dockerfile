FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/csvtosql-0.1.jar CsvToSql.jar
ENTRYPOINT ["java", "-jar", "CsvToSql.jar"]