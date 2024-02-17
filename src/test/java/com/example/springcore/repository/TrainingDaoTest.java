package com.example.springcore.repository;

import com.example.springcore.model.Training;
import com.example.springcore.repository.impl.TrainingDao;
import com.example.springcore.storage.impl.TrainingStorage;
import com.example.springcore.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingDaoTest {
    @Mock
    private TrainingStorage trainingStorage;

    @InjectMocks
    private TrainingDao trainingDao;

    @Test
    void createTraining() {
        // Given
        Training training = TestUtil.createTestTraining();
        Map<Integer, Training> storageMap = new HashMap<>();
        when(trainingStorage.getStorageMap()).thenReturn(storageMap);

        // When
        Training result = trainingDao.save(training);

        // Then
        assertThat(result, samePropertyValuesAs(training));
        assertEquals(1, storageMap.size());
        assertTrue(storageMap.containsValue(training));
    }

    @Test
    void getTraining() {
        // Given
        Training expectedTraining = TestUtil.createTestTraining();
        Map<Integer, Training> storageMap = new HashMap<>();
        storageMap.put(1, expectedTraining);
        when(trainingStorage.getStorageMap()).thenReturn(storageMap);

        // When
        Optional<Training> result = trainingDao.get(1);

        // Then
        assertTrue(result.isPresent());
        assertThat(result.get(), samePropertyValuesAs(expectedTraining));
    }

    @Test
    void getAll() {
        // Given
        Training training1 = TestUtil.createTestTraining();
        Training training2 = TestUtil.createTestTraining();
        Map<Integer, Training> storageMap = new HashMap<>();
        storageMap.put(1, training1);
        storageMap.put(2, training2);
        when(trainingStorage.getStorageMap()).thenReturn(storageMap);

        // When
        List<Training> result = trainingDao.getAll();

        // Then
        assertEquals(2, result.size());
        assertThat(result.get(0), samePropertyValuesAs(training1));
        assertThat(result.get(1), samePropertyValuesAs(training2));
    }
}