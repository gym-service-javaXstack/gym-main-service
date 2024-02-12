package com.example.springcore.service;

import com.example.springcore.model.Trainer;
import com.example.springcore.repository.TrainerDao;
import com.example.springcore.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {
    @Mock
    private TrainerDao trainerDao;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    void createTrainer() {
        // Given
        Trainer trainer = TestUtil.createTestTrainer();
        when(profileService.generateUsername(anyString(), anyString())).thenReturn("username");
        when(profileService.generatePassword()).thenReturn("password");
        when(trainerDao.save(any(Trainer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Trainer result = trainerService.createTrainer(trainer);

        // Then
        assertThat(result, samePropertyValuesAs(trainer));
        verify(trainerDao, times(1)).save(any(Trainer.class));
    }

    @Test
    void updateTrainer() {
        // Given
        Trainer trainer = TestUtil.createTestTrainer();
        when(trainerDao.update(any(Trainer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Trainer result = trainerService.updateTrainer(trainer);

        // Then
        assertThat(result, samePropertyValuesAs(trainer));
        verify(trainerDao, times(1)).update(any(Trainer.class));
    }

    @Test
    void getTrainer() {
        // Given
        Trainer expectedTrainer = TestUtil.createTestTrainer();
        when(trainerDao.get(anyInt())).thenReturn(expectedTrainer);

        // When
        Trainer result = trainerService.getTrainer(1);

        // Then
        assertThat(result, samePropertyValuesAs(expectedTrainer));
    }

    @Test
    void getAllTrainers() {
        // Given
        List<Trainer> expectedTrainers = Arrays.asList(TestUtil.createTestTrainer(), TestUtil.createTestTrainer());
        when(trainerDao.getAll()).thenReturn(expectedTrainers);

        // When
        List<Trainer> result = trainerService.getAllTrainers();

        // Then
        assertEquals(expectedTrainers.size(), result.size());
        for (int i = 0; i < expectedTrainers.size(); i++) {
            assertThat(result.get(i), samePropertyValuesAs(expectedTrainers.get(i)));
        }
    }
}