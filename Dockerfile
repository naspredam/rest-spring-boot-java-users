FROM openjdk:15.0.1-jdk AS builder

COPY . /source

WORKDIR /source

RUN ./gradlew build

FROM openjdk:15.0.1-slim-buster

RUN adduser --disabled-password --gecos '' spring

USER spring

ARG JAR_FILE=/source/build/libs/*.jar
COPY --from=builder ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]