FROM openjdk:17-alpine
COPY target/gym-main-service-0.0.1-SNAPSHOT.jar /gym-main-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/gym-main-service-0.0.1-SNAPSHOT.jar"]