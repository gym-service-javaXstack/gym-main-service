FROM openjdk:17-alpine
COPY target/SpringCore-0.0.1-SNAPSHOT.jar /SpringCore-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/SpringCore-0.0.1-SNAPSHOT.jar"]