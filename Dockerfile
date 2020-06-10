FROM openjdk:8-jdk-alpine
COPY target/csvtosql-0.1.jar csvToSql.jar
VOLUME /tmp
CMD ["java", "-jar", "csvToSql.jar"]