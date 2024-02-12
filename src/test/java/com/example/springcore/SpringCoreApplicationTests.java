package com.example.springcore;

import com.example.springcore.storage.impl.TraineeStorage;
import com.example.springcore.storage.impl.TrainerStorage;
import com.example.springcore.storage.impl.TrainingStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SpringCoreApplicationTests {
    @Autowired
    private TrainerStorage trainerStorage;

    @Autowired
    private TraineeStorage traineeStorage;

    @Autowired
    private TrainingStorage trainingStorage;

    @Test
    void contextLoads() {
    }

    @Test
    void testBeansExist() {
        assertNotNull(trainerStorage, "TrainerStorage bean should exist in the context");
        assertNotNull(traineeStorage, "TraineeStorage bean should exist in the context");
        assertNotNull(trainingStorage, "TrainingStorage bean should exist in the context");
    }
}
