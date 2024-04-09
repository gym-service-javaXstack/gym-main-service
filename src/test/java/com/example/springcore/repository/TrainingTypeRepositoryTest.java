package com.example.springcore.repository;

import com.example.springcore.model.TrainingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class TrainingTypeRepositoryTest {

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Test
    void shouldReturnOptionalOfTrainingType_WhenFindByTrainingTypeName() {
        // Arrange
        // data.sql already uploaded data
        String targetTrainingTypeName = "Boxing";

        // Act
        Optional<TrainingType> foundTrainingType = trainingTypeRepository.findTrainingTypeByTrainingTypeName(targetTrainingTypeName);

        // Assert
        assertTrue(foundTrainingType.isPresent());
        assertEquals(targetTrainingTypeName, foundTrainingType.get().getTrainingTypeName());
    }
}