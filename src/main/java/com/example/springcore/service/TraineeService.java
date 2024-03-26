package com.example.springcore.service;

import com.example.springcore.dto.TraineeDTO;
import com.example.springcore.dto.TraineeWithTrainerListToUpdateRequestDTO;
import com.example.springcore.dto.TraineeWithTrainersDTO;
import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TraineeWithTrainersMapper;
import com.example.springcore.mapper.TrainerMapper;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.User;
import com.example.springcore.repository.TraineeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeRepository traineeRepository;

    private final TrainerService trainerService;
    private final ProfileService profileService;
    //private final AuthenticationService authenticationService;

    private final TrainerMapper trainerMapper;
    private final TraineeWithTrainersMapper traineeWithTrainersMapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserCredentialsDTO createTrainee(TraineeDTO traineeDTO) {
        log.info("Enter TraineeService createTrainee traineeDTO");
        String generatedPassword = profileService.generatePassword();

        User user = User.builder()
                .firstName(traineeDTO.getFirstName())
                .lastName(traineeDTO.getLastName())
                .userName(profileService.generateUsername(traineeDTO.getFirstName(), traineeDTO.getLastName()))
                .password(passwordEncoder.encode(generatedPassword))
                .isActive(false)
                .build();

        Trainee traineeToSave = Trainee.builder()
                .user(user)
                .address(traineeDTO.getAddress())
                .dateOfBirth(traineeDTO.getDateOfBirth())
                .trainers(new HashSet<>())
                .build();

        traineeRepository.save(traineeToSave);

        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setUsername(user.getUserName());
        userCredentialsDTO.setPassword(generatedPassword);

        log.info("Exit TraineeService createTrainee traineeDTO: {}", user.getUserName());
        return userCredentialsDTO;
    }

    @Transactional
    public TraineeWithTrainersDTO updateTrainee(TraineeDTO traineeDTO) {
        log.info("Enter TraineeService updateTrainee");

       // authenticationService.isAuthenticated(traineeDTO.getUserName());

        Trainee trainee = traineeRepository.getTraineeByUser_UserName(traineeDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username " + traineeDTO.getUserName() + " not found"));

        traineeWithTrainersMapper.updateTraineeFromDTO(traineeDTO, trainee);

        Trainee updated = traineeRepository.save(trainee);

        log.info("Exit TraineeService updateTrainee trainee: {}", updated.getUser().getId());
        return traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(updated);
    }

    @Transactional
    public void deleteTrainee(String username) {
        log.info("Enter TraineeService deleteTrainee trainee: {}", username);

       // authenticationService.isAuthenticated(username);

        Trainee trainee = traineeRepository.getTraineeByUser_UserName(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username " + username + " not found"));

        traineeRepository.delete(trainee);
        log.info("Exit TraineeService deleteTrainee trainee: {}", username);
    }

    @Transactional(readOnly = true)
    public TraineeWithTrainersDTO getTraineeDTOByUsername(String username) {
        log.info("Enter TraineeService  getTraineeDTOByUsername trainee: {}", username);
       // authenticationService.isAuthenticated(username);

        Trainee trainee = traineeRepository.getTraineeByUser_UserName(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username " + username + " not found"));

        log.info("Exit TraineeService getTraineeDTOByUsername trainee: {}", username);
        return traineeWithTrainersMapper.fromTraineeToTraineeWithTrainersDTO(trainee);
    }

    @Transactional(readOnly = true)
    public Trainee getTraineeByUsername(String username) {
        log.info("Enter TraineeService  getTraineeDTOByUsername trainee: {}", username);

        Trainee trainee = traineeRepository.getTraineeByUser_UserName(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username " + username + " not found"));

        log.info("Exit TraineeService getTraineeDTOByUsername trainee: {}", username);
        return trainee;
    }

    @Transactional
    public void linkTraineeAndTrainee(Trainee trainee, Trainer trainer) {
        log.info("Enter TraineeService  linkTraineeAndTrainee: {} with {}", trainee.getId(), trainer.getId());

        if (!trainee.getTrainers().contains(trainer)) {
            trainee.addTrainer(trainer);
            traineeRepository.save(trainee);
        }

        log.info("Exit TraineeService linkTraineeAndTrainee: {} with {}", trainee.getId(), trainer.getId());
    }

    @Transactional
    public List<TrainerDTO> updateTrainersListInTraineeByUsername(TraineeWithTrainerListToUpdateRequestDTO traineeWithTrainerListToUpdateRequestDTO) {
        log.info("Enter TraineeService updateTrainersListInTraineeByUsername");

       // authenticationService.isAuthenticated(traineeWithTrainerListToUpdateRequestDTO.getUserName());

        Trainee trainee = traineeRepository.getTraineeByUser_UserName(traineeWithTrainerListToUpdateRequestDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException("Trainee with username " + traineeWithTrainerListToUpdateRequestDTO.getUserName() + " not found"));

        List<Trainer> trainersByUsernameList = trainerService.getTrainersByUsernameList(
                traineeWithTrainerListToUpdateRequestDTO.getTrainers().stream()
                        .map(trainerDTO -> trainerDTO.getUserName())
                        .toList()
        );

        trainee.setTrainers(new HashSet<>(trainersByUsernameList));

        traineeRepository.save(trainee);

        log.info("Exit TraineeService updateTrainersListInTraineeByUsername: {}", trainee.getId());
        return trainerMapper.fromTrainerListToTrainerListDTO(trainersByUsernameList);
    }

    @Transactional(readOnly = true)
    public List<TrainerDTO> getTrainersNotAssignedToTrainee(String username) {
        log.info("Enter TraineeService getTrainersNotAssignedToTrainee method: {}", username);

        //authenticationService.isAuthenticated(username);

        List<Trainer> trainersNotAssignedToTrainee = traineeRepository.getTrainersNotAssignedToTrainee(username);

        log.info("Exit TraineeService getTrainersNotAssignedToTrainee method: {}", username);
        return trainerMapper.fromTrainerListToTrainerListDTO(trainersNotAssignedToTrainee);
    }
}
