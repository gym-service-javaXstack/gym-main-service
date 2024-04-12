# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.2/maven-plugin/reference/html/)s
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.2/maven-plugin/reference/html/#build-image)


# How to run in docker:

1) Use "**mvn clean install -DskipTests**" in root folder for creating .jar
2) Use "**docker build -t gym-main .**" to create image from .jar
4) Use "**docker-compose -f docker-compose_file_name(USE DEV OR PROD version) up -d**" to up containers
5) To open swagger use http://localhost:8080/swagger-ui/index.html#/
6) Optional: if u want to check db, use pgAdmin via this link: **http://localhost:80/**
    - user: **pgadmin4@pgadmin.org**
    - password: **postgres**
7) Then use password to registry server:
![img.png](img.png)
8) Prometheus works after deploying docker-compose at  **http://localhost:9090/**