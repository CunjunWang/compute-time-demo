FROM maven:3.6.3 AS maven
WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package

FROM openjdk:8-jdk-alpine

ARG JAR_FILE=compute-time-1.0-SNAPSHOT.jar

WORKDIR /opt/app

# Copy the spring-boot-api-tutorial.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["mvn","spring-boot:run","-Dspring-boot.run.profiles=prod"]