package com.example.springcore.service;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.mapper.TraineeWithTrainersMapper;
import com.example.springcore.mapper.TrainerMapper;
import com.example.springcore.mapper.TrainingMapper;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
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
    private final TrainerService trainerService;
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;
    private final TrainerMapper trainerMapper;
    private final TraineeWithTrainersMapper traineeWithTrainersMapper;
    private final TrainingMapper trainingMapper;

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

        log.info("TraineeService createTrainee traineeDTO: {}", saved.getUser().getId());
        return saved;
    }

    @Transactional
    public TraineeWithTrainersDTO updateTrainee(TraineeDTO traineeDTO) {
        authenticationService.isAuthenticated(traineeDTO.getUserName());

        Trainee trainee = traineeDao.getTraineeByUsername(traineeDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException(traineeDTO.getUserName()));

        traineeWithTrainersMapper.updateTraineeFromDTO(traineeDTO, trainee);

        Trainee updated = traineeDao.update(trainee);

        log.info("TraineeService updateTrainee trainee: {}", updated.getUser().getId());
        return traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(updated);
    }

    @Transactional
    public void deleteTrainee(String username) {
        authenticationService.isAuthenticated(username);

        Trainee trainee = traineeDao.getTraineeByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username " + username + " not found"));

        traineeDao.delete(trainee);
        log.info("TraineeService deleteTrainee trainee: {}", username);
    }

    @Transactional(readOnly = true)
    public TraineeWithTrainersDTO getTraineeByUsername(String username) {
        authenticationService.isAuthenticated(username);

        Trainee trainee = traineeDao.getTraineeByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(username));

        log.info("TraineeService getTraineeByUsername trainee: {}", username);
        return traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(trainee);
    }

    @Transactional
    public void linkTraineeAndTrainee(Trainee trainee, Trainer trainer) {
        traineeDao.linkTraineeAndTrainee(trainee, trainer);
        log.info("TraineeService linkTraineeAndTrainee: {} with {}", trainee.getId(), trainer.getId());
    }


    @Transactional
    public List<TrainerDTO> updateTrainersListInTraineeByUsername(TraineeWithTrainersDTO traineeWithTrainersDTO) {
        Trainee trainee = traineeDao.getTraineeByUsername(traineeWithTrainersDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException(traineeWithTrainersDTO.getUserName()));

        List<Trainer> trainersByUsernameList = trainerService.getTrainersByUsernameList(
                traineeWithTrainersDTO.getTrainers().stream()
                        .map(trainerDTO -> trainerDTO.getUserName())
                        .toList()
        );

        trainee.setTrainers(new HashSet<>(trainersByUsernameList));

        traineeDao.save(trainee);

        log.info("TraineeService updateTrainersListInTraineeByUsername: {}", trainee.getId());
        return trainerMapper.fromTrainerListToTrainerListDTO(trainersByUsernameList);
    }


    @Transactional(readOnly = true)
    public List<TrainerDTO> getTrainersNotAssignedToTrainee(String username) {
        authenticationService.isAuthenticated(username);

        List<Trainer> trainersNotAssignedToTrainee = traineeDao.getTrainersNotAssignedToTrainee(username);

        log.info("TraineeService getTrainersNotAssignedToTrainee method: {}", username);
        return trainerMapper.fromTrainerListToTrainerListDTO(trainersNotAssignedToTrainee);
    }

    @Transactional(readOnly = true)
    public List<TrainingDTO> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerUsername, String trainingTypeName) {
        authenticationService.isAuthenticated(username);

        List<Training> traineeTrainingsByCriteria = traineeDao.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerUsername, trainingTypeName);

        log.info("TraineeService getTraineeTrainingsByCriteria method: {}", username);
        return trainingMapper.fromTrainingListToTraineeTrainingListDTO(traineeTrainingsByCriteria);
    }
}
