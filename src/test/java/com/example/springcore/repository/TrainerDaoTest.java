package com.example.springcore.repository;

import com.example.springcore.model.Trainer;
import com.example.springcore.repository.impl.TrainerDao;
import com.example.springcore.storage.impl.TrainerStorage;
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
class TrainerDaoTest {
    @Mock
    private TrainerStorage trainerStorage;

    @InjectMocks
    private TrainerDao trainerDao;

    @Test
    void get() {
        // Given
        Trainer expectedTrainer = TestUtil.createTestTrainer();
        Map<Integer, Trainer> storageMap = new HashMap<>();
        storageMap.put(1, expectedTrainer);
        when(trainerStorage.getStorageMap()).thenReturn(storageMap);

        // When
        Optional<Trainer> result = trainerDao.get(1);

        // Then
        assertTrue(result.isPresent());
        assertThat(result.get(), samePropertyValuesAs(expectedTrainer));
    }

    @Test
    void save() {
        // Given
        Trainer trainer = TestUtil.createTestTrainer();
        Map<Integer, Trainer> storageMap = new HashMap<>();
        when(trainerStorage.getStorageMap()).thenReturn(storageMap);

        // When
        Trainer result = trainerDao.save(trainer);

        // Then
        assertThat(result, samePropertyValuesAs(trainer));
        assertEquals(1, storageMap.size());
        assertTrue(storageMap.containsValue(trainer));
    }

    @Test
    void update() {
        // Given
        Trainer trainer = TestUtil.createTestTrainer();
        trainer.setUserId(1);
        Map<Integer, Trainer> storageMap = new HashMap<>();
        storageMap.put(1, trainer);
        when(trainerStorage.getStorageMap()).thenReturn(storageMap);

        // When
        Trainer result = trainerDao.update(trainer,trainer.getUserId());

        // Then
        assertThat(result, samePropertyValuesAs(trainer));
    }

    @Test
    void getAll() {
        // Given
        Trainer trainer1 = TestUtil.createTestTrainer();
        Trainer trainer2 = TestUtil.createTestTrainer();
        Map<Integer, Trainer> storageMap = new HashMap<>();
        storageMap.put(1, trainer1);
        storageMap.put(2, trainer2);
        when(trainerStorage.getStorageMap()).thenReturn(storageMap);

        // When
        List<Trainer> result = trainerDao.getAll();

        // Then
        assertEquals(2, result.size());
        assertThat(result.get(0), samePropertyValuesAs(trainer1));
        assertThat(result.get(1), samePropertyValuesAs(trainer2));
    }
}