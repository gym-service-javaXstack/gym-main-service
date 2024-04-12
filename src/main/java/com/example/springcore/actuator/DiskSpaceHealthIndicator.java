package com.example.springcore.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DiskSpaceHealthIndicator implements HealthIndicator {

    private static final File FILE = new File(".");

    @Override
    public Health health() {
        long freeSpace = FILE.getFreeSpace();
        return Health.up()
                .withDetail("freeSpace", freeSpace)
                .build();
    }
}
