package com.example.springcore.service;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.mapper.TraineeWithTrainersMapper;
import com.example.springcore.mapper.TrainerMapper;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TraineeDao;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeDao traineeDao;
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;
    private final TrainerMapper trainerMapper;
    private final TraineeWithTrainersMapper traineeWithTrainersMapper;

    @Transactional
    public Trainee createTrainee(TraineeDTO traineeDTO) {
        User user = User.builder()
                .firstName(traineeDTO.getFirstName())
                .lastName(traineeDTO.getLastName())
                .userName(profileService.generateUsername(traineeDTO.getFirstName(), traineeDTO.getLastName()))
                .password(profileService.generatePassword())
                .isActive(false)
                .build();

        Trainee traineeToSave = Trainee.builder()
                .user(user)
                .address(traineeDTO.getAddress())
                .dateOfBirth(traineeDTO.getDateOfBirth())
                .trainers(new HashSet<>())
                .build();

        Trainee saved = traineeDao.save(traineeToSave);
        log.info("Created traineeDTO: {}", saved.getUser().getId());
        return saved;
    }

    @Transactional
    public TraineeWithTrainersDTO updateTrainee(TraineeDTO traineeDTO) {
        authenticationService.isAuthenticated(traineeDTO.getUserName());

        Trainee trainee = traineeDao.getTraineeByUsername(traineeDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException(traineeDTO.getUserName()));

        traineeWithTrainersMapper.updateTraineeFromDTO(traineeDTO, trainee);

        Trainee updated = traineeDao.update(trainee);
        log.info("Updated trainee: {}", updated.getUser().getId());
        return traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(updated);
    }

    @Transactional
    public void deleteTrainee(String username) {
        authenticationService.isAuthenticated(username);
        Trainee trainee = traineeDao.getTraineeByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username " + username + " not found"));
        traineeDao.delete(trainee);
        log.info("Deleted trainee: {}", username);
    }

    @Transactional(readOnly = true)
    public TraineeWithTrainersDTO getTraineeByUsername(String username) {
        authenticationService.isAuthenticated(username);
        Trainee trainee = traineeDao.getTraineeByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
        log.info("getTraineeByUsername trainee: {}", username);
        return traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(trainee);
    }

    @Transactional
    public void updateTraineesTrainersList(Trainee trainee, Trainer trainer) {
        traineeDao.updateTraineesTrainersList(trainee, trainer);
        log.info("Updating trainee's trainers list: {} with {}", trainee.getId(), trainer.getId());
    }

    @Transactional(readOnly = true)
    public List<TrainerDTO> getTrainersNotAssignedToTrainee(String username) {
        authenticationService.isAuthenticated(username);

        List<Trainer> trainersNotAssignedToTrainee = traineeDao.getTrainersNotAssignedToTrainee(username);
        log.info("getTrainersNotAssignedToTrainee method: {}", username);
        return trainerMapper.fromTrainerListToTrainerListDTO(trainersNotAssignedToTrainee);
    }

    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, TrainingType trainingType) {
        authenticationService.isAuthenticated(username);
        List<Training> traineeTrainingsByCriteria = traineeDao.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerName, trainingType);
        log.info("getTraineeTrainingsByCriteria method: {}", username);
        return traineeTrainingsByCriteria;
    }
}
