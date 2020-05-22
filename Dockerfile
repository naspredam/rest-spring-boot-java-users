FROM openjdk:14-jdk-slim

RUN adduser --disabled-password --gecos '' spring

USER spring:spring

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]