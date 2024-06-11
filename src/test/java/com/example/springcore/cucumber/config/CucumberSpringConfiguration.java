package com.example.springcore.cucumber.config;

import jakarta.annotation.PreDestroy;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

public class CucumberSpringConfiguration {

    protected static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:latest")
            .withExposedPorts(5432)
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("gymDB-test");

    protected static final GenericContainer<?> REDIS = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379)
            .withCommand("redis-server --requirepass redis");

    protected static final GenericContainer<?> ACTIVE_MQ = new GenericContainer<>("apache/activemq-artemis:latest")
            .withExposedPorts(61616, 8161)
            .withEnv("ARTEMIS_USER", "activemq")
            .withEnv("ARTEMIS_PASSWORD", "activemq");

    static {
        POSTGRES.start();
        REDIS.start();
        ACTIVE_MQ.start();
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);

        registry.add("spring.data.redis.host", REDIS::getHost);
        registry.add("spring.data.redis.username", () -> "default");
        registry.add("spring.data.redis.password", () -> "redis");
        registry.add("spring.data.redis.port", () -> REDIS.getMappedPort(6379));

        registry.add("spring.artemis.user", () -> "activemq");
        registry.add("spring.artemis.password", () -> "activemq");
        registry.add("spring.artemis.broker-url",
                () -> "tcp://localhost:" + ACTIVE_MQ.getMappedPort(61616));
    }

    @PreDestroy
    public void shutdown() {
        POSTGRES.stop();
        REDIS.stop();
        ACTIVE_MQ.stop();
    }
}
