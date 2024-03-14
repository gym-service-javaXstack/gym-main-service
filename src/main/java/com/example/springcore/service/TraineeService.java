package com.example.springcore.service;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.dto.request.CreateTraineeRequestDTO;
import com.example.springcore.dto.request.UpdateTraineesTrainersListRequestDTO;
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
    public Trainee createTrainee(CreateTraineeRequestDTO createTraineeRequestDTO) {
        log.info("Enter TraineeService createTrainee traineeDTO");

        User user = User.builder()
                .firstName(createTraineeRequestDTO.getFirstName())
                .lastName(createTraineeRequestDTO.getLastName())
                .userName(profileService.generateUsername(createTraineeRequestDTO.getFirstName(), createTraineeRequestDTO.getLastName()))
                .password(profileService.generatePassword())
                .isActive(false)
                .build();

        Trainee traineeToSave = Trainee.builder()
                .user(user)
                .address(createTraineeRequestDTO.getAddress())
                .dateOfBirth(createTraineeRequestDTO.getDateOfBirth())
                .trainers(new HashSet<>())
                .build();

        Trainee saved = traineeDao.save(traineeToSave);

        log.info("Exit TraineeService createTrainee traineeDTO: {}", saved.getUser().getId());
        return saved;
    }

    @Transactional
    public TraineeWithTrainersDTO updateTrainee(TraineeDTO traineeDTO) {
        log.info("Enter TraineeService updateTrainee");

        authenticationService.isAuthenticated(traineeDTO.getUserName());

        Trainee trainee = traineeDao.getTraineeByUsername(traineeDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException(traineeDTO.getUserName()));

        traineeWithTrainersMapper.updateTraineeFromDTO(traineeDTO, trainee);

        Trainee updated = traineeDao.update(trainee);

        log.info("Exit TraineeService updateTrainee trainee: {}", updated.getUser().getId());
        return traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(updated);
    }

    @Transactional
    public void deleteTrainee(String username) {
        log.info("Enter TraineeService deleteTrainee trainee: {}", username);

        authenticationService.isAuthenticated(username);

        Trainee trainee = traineeDao.getTraineeByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username " + username + " not found"));

        traineeDao.delete(trainee);
        log.info("Exit TraineeService deleteTrainee trainee: {}", username);
    }

    @Transactional(readOnly = true)
    public TraineeWithTrainersDTO getTraineeByUsername(String username) {
        log.info("Enter TraineeService  getTraineeByUsername trainee: {}", username);
        authenticationService.isAuthenticated(username);

        Trainee trainee = traineeDao.getTraineeByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(username));

        log.info("Exit TraineeService getTraineeByUsername trainee: {}", username);
        return traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(trainee);
    }

    @Transactional
    public void linkTraineeAndTrainee(Trainee trainee, Trainer trainer) {
        log.info("Enter TraineeService  linkTraineeAndTrainee: {} with {}", trainee.getId(), trainer.getId());
        traineeDao.linkTraineeAndTrainee(trainee, trainer);
        log.info("Exit TraineeService linkTraineeAndTrainee: {} with {}", trainee.getId(), trainer.getId());
    }


    @Transactional
    public List<TrainerDTO> updateTrainersListInTraineeByUsername(UpdateTraineesTrainersListRequestDTO updateTraineesTrainersListRequestDTO) {
        log.info("Enter TraineeService updateTrainersListInTraineeByUsername");

        authenticationService.isAuthenticated(updateTraineesTrainersListRequestDTO.getUserName());

        Trainee trainee = traineeDao.getTraineeByUsername(updateTraineesTrainersListRequestDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException(updateTraineesTrainersListRequestDTO.getUserName()));

        List<Trainer> trainersByUsernameList = trainerService.getTrainersByUsernameList(
                updateTraineesTrainersListRequestDTO.getTrainers().stream()
                        .map(trainerDTO -> trainerDTO.getUserName())
                        .toList()
        );

        trainee.setTrainers(new HashSet<>(trainersByUsernameList));

        traineeDao.save(trainee);

        log.info("Exit TraineeService updateTrainersListInTraineeByUsername: {}", trainee.getId());
        return trainerMapper.fromTrainerListToTrainerListDTO(trainersByUsernameList);
    }

    @Transactional(readOnly = true)
    public List<TrainerDTO> getTrainersNotAssignedToTrainee(String username) {
        log.info("Enter TraineeService getTrainersNotAssignedToTrainee method: {}", username);

        authenticationService.isAuthenticated(username);

        List<Trainer> trainersNotAssignedToTrainee = traineeDao.getTrainersNotAssignedToTrainee(username);

        log.info("Exit TraineeService getTrainersNotAssignedToTrainee method: {}", username);
        return trainerMapper.fromTrainerListToTrainerListDTO(trainersNotAssignedToTrainee);
    }

    @Transactional(readOnly = true)
    public List<TrainingDTO> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerUsername, String trainingTypeName) {
        log.info("Enter TraineeService getTraineeTrainingsByCriteria method: {}", username);

        authenticationService.isAuthenticated(username);

        List<Training> traineeTrainingsByCriteria = traineeDao.getTraineeTrainingsByCriteria(username, fromDate, toDate, trainerUsername, trainingTypeName);

        log.info("Exit TraineeService getTraineeTrainingsByCriteria method: {}", username);
        return trainingMapper.fromTrainingListToTraineeTrainingListDTO(traineeTrainingsByCriteria);
    }
}
