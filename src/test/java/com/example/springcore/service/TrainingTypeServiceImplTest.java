package com.example.springcore.service;

import com.example.springcore.model.TrainingType;
import com.example.springcore.repository.TrainingTypeRepository;
import com.example.springcore.service.impl.TrainingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingTypeServiceImplTest {
    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeServiceImpl trainingTypeServiceImpl;

    private TrainingType testTrainingType;

    @BeforeEach
    public void setUp() {
        testTrainingType = new TrainingType();
        testTrainingType.setTrainingTypeName("testTrainingType");
    }

    @Test
    void testFindTrainingTypeByName() {
        when(trainingTypeRepository.findTrainingTypeByTrainingTypeName("testTrainingType")).thenReturn(Optional.ofNullable(testTrainingType));

        TrainingType trainingType = trainingTypeServiceImpl.findTrainingTypeByName("testTrainingType");

        assertThat(trainingType, is(testTrainingType));

        verify(trainingTypeRepository, times(1)).findTrainingTypeByTrainingTypeName("testTrainingType");
    }

    @Test
    void testGetTrainingTypeList() {
        List<TrainingType> trainingTypes = Arrays.asList(testTrainingType);
        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);

        List<TrainingType> result = trainingTypeServiceImpl.getTrainingTypeList();

        assertThat(result, is(trainingTypes));

        verify(trainingTypeRepository, times(1)).findAll();
    }
}