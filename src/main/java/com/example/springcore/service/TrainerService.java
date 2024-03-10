package com.example.springcore.service;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.mapper.TrainerWithTraineesMapper;
import com.example.springcore.mapper.TrainingMapper;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TrainerDao;
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
public class TrainerService {
    private final TrainerDao trainerDao;
    private final ProfileService profileService;
    private final AuthenticationService authenticationService;
    private final TrainingTypeService trainingTypeService;
    private final TrainerWithTraineesMapper trainerWithTraineesMapper;
    private final TrainingMapper trainingMapper;

    @Transactional
    public Trainer createTrainer(TrainerDTO trainerDTO) {
        User user = User.builder()
                .firstName(trainerDTO.getFirstName())
                .lastName(trainerDTO.getLastName())
                .userName(profileService.generateUsername(trainerDTO.getFirstName(), trainerDTO.getLastName()))
                .password(profileService.generatePassword())
                .isActive(false)
                .build();


        Trainer trainerToSave = Trainer.builder()
                .user(user)
                .specialization(trainingTypeService.findTrainingTypeByName(trainerDTO.getSpecialization().getTrainingTypeName()))
                .trainees(new HashSet<>())
                .build();

        Trainer saved = trainerDao.save(trainerToSave);
        log.info("TrainerService createTrainer Trainer: {}", trainerToSave.getUser().getId());
        return saved;
    }

    @Transactional
    public TrainerWithTraineesDTO updateTrainer(TrainerDTO trainerDTO) {
        authenticationService.isAuthenticated(trainerDTO.getUserName());

        Trainer trainer = trainerDao.getTrainerByUsername(trainerDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException(trainerDTO.getUserName()));

        TrainingType trainingTypeByNameToUpdate = trainingTypeService.findTrainingTypeByName(trainerDTO.getSpecialization().getTrainingTypeName());

        trainerWithTraineesMapper.updateTrainerFromDTO(trainerDTO, trainer);
        trainer.setSpecialization(trainingTypeByNameToUpdate);

        Trainer updated = trainerDao.update(trainer);

        log.info("TrainerService updateTrainer trainer: {}", trainerDTO.getUserName());
        return trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(updated);
    }

    @Transactional(readOnly = true)
    public TrainerWithTraineesDTO getTrainerByUsername(String username) {
        authenticationService.isAuthenticated(username);
        Trainer trainer = trainerDao.getTrainerByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
        log.info("TrainerService getTrainerByUsername trainer: {}", username);
        return trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(trainer);
    }

    @Transactional(readOnly = true)
    public List<TrainingDTO> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeUserName) {
        authenticationService.isAuthenticated(username);
        List<Training> trainerTrainingsByCriteria = trainerDao.getTrainerTrainingsByCriteria(username, fromDate, toDate, traineeUserName);
        log.info("TrainerService getTrainerTrainingsByCriteria method: {}", username);
        return trainingMapper.fromTrainingListToTraineeTrainingListDTO(trainerTrainingsByCriteria);
    }

    @Transactional(readOnly = true)
    public List<Trainer> getTrainersByUsernameList(List<String> trainerUsernames) {
        List<Trainer> trainersByUsernameList = trainerDao.getTrainersByUsernameList(trainerUsernames);
        log.info("TrainerService getTrainersByUsernameList method: {}", trainerUsernames);
        return trainersByUsernameList;
    }
}
