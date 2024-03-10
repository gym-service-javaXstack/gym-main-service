package com.example.springcore.service;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TrainingDao;
import com.example.springcore.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrainingServiceTest {

    @Mock
    private TrainingDao trainingDao;

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TrainingService trainingService;

 /*   @Test
    void createTraining() {
        // Given
        User user = TestUtil.createUser("traineeUser", "password");
        Trainee trainee = TestUtil.createTrainee(user, null);
        TrainingType trainingType = TestUtil.createTrainingType("specialization");
        Trainer trainer = TestUtil.createTrainer(user, trainingType);
        String trainingName = "testTraining";
        LocalDate trainingDate = LocalDate.now();
        Integer duration = 60;

        // When
        trainingService.createTraining(trainee, trainer, trainingName, trainingType, trainingDate, duration);

        // Then
        verify(traineeService).updateTraineesTrainersList(trainee, trainer);
        ArgumentCaptor<Training> trainingCaptor = ArgumentCaptor.forClass(Training.class);
        verify(trainingDao).save(trainingCaptor.capture());
        Training savedTraining = trainingCaptor.getValue();
        assertEquals(trainee, savedTraining.getTrainee());
        assertEquals(trainer, savedTraining.getTrainer());
        assertEquals(trainingName, savedTraining.getTrainingName());
        assertEquals(trainingType, savedTraining.getTrainingType());
        assertEquals(trainingDate, savedTraining.getTrainingDate());
        assertEquals(duration, savedTraining.getDuration());
    }*/
}