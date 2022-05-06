FROM maven:3.8.4-openjdk-17-slim as build

COPY . .
RUN mvn install -q -DskipTests

FROM openjdk:17.0.2-jdk-oraclelinux8

LABEL schedlerdavid=david.schedler@stud.fh-campuswien.ac.at
LABEL caf3babe=nichil.strasser@stud.fh-campuswien.ac.at
LABEL schneidermichael=michael.schneider@stud-fh.campusvienna.ac.at

ARG JAR_FILE=target/*.jar
COPY --from=build ${JAR_FILE} discovery-service.jar

ENV PROFILE=dev
ENV SERVER_PORT="8070"
ENV CONFIGSERVER_URI="http://configurationservice:8071"
ENV CONFIGSERVER_PORT="8071"

ENTRYPOINT ["java","-jar","/discovery-service.jar"]