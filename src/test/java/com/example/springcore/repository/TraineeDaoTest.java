package com.example.springcore.repository;

import com.example.springcore.model.Trainee;
import com.example.springcore.repository.impl.TraineeDao;
import com.example.springcore.storage.impl.TraineeStorage;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeDaoTest {
    @Mock
    private TraineeStorage traineeStorage;

    @InjectMocks
    private TraineeDao traineeDao;

    @Test
    void get() {
        // Given
        Trainee expectedTrainee = TestUtil.createTestTrainee();
        Map<Integer, Trainee> storageMap = new HashMap<>();
        storageMap.put(1, expectedTrainee);
        when(traineeStorage.getStorageMap()).thenReturn(storageMap);

        // When
        Optional<Trainee> result = traineeDao.get(1);

        // Then
        assertTrue(result.isPresent());
        assertThat(result.get(), samePropertyValuesAs(expectedTrainee));
    }

    @Test
    void save() {
        // Given
        Trainee trainee = TestUtil.createTestTrainee();
        Map<Integer, Trainee> storageMap = new HashMap<>();
        when(traineeStorage.getStorageMap()).thenReturn(storageMap);

        // When
        Trainee result = traineeDao.save(trainee);

        // Then
        assertThat(result, samePropertyValuesAs(trainee));
        assertEquals(1, storageMap.size());
        assertTrue(storageMap.containsValue(trainee));
    }

    @Test
    void update() {
        // Given
        Trainee trainee = TestUtil.createTestTrainee();
        trainee.setUserId(1);
        Map<Integer, Trainee> storageMap = new HashMap<>();
        storageMap.put(1, trainee);
        when(traineeStorage.getStorageMap()).thenReturn(storageMap);

        // When
        Trainee result = traineeDao.update(trainee, trainee.getUserId());

        // Then
        assertThat(result, samePropertyValuesAs(trainee));
    }

    @Test
    void getAll() {
        // Given
        Trainee trainee1 = TestUtil.createTestTrainee();
        Trainee trainee2 = TestUtil.createTestTrainee();
        Map<Integer, Trainee> storageMap = new HashMap<>();
        storageMap.put(1, trainee1);
        storageMap.put(2, trainee2);
        when(traineeStorage.getStorageMap()).thenReturn(storageMap);

        // When
        List<Trainee> result = traineeDao.getAll();

        // Then
        assertEquals(2, result.size());
        assertThat(result.get(0), samePropertyValuesAs(trainee1));
        assertThat(result.get(1), samePropertyValuesAs(trainee2));
    }

    @Test
    void delete() {
        // Given
        Trainee trainee = TestUtil.createTestTrainee();
        trainee.setUserId(1);
        Map<Integer, Trainee> storageMap = new HashMap<>();
        storageMap.put(1, trainee);
        when(traineeStorage.getStorageMap()).thenReturn(storageMap);

        // When
        traineeDao.delete(1);

        // Then
        assertTrue(storageMap.isEmpty());
    }
}