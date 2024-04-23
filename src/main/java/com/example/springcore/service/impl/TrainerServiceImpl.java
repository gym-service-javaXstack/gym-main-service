package com.example.springcore.service.impl;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.feign.GymReportsClient;
import com.example.springcore.mapper.TrainerWithTraineesMapper;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TrainerRepository;
import com.example.springcore.service.ProfileService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingTypeService;
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
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;

    private final ProfileService profileService;
    private final TrainingTypeService trainingTypeService;

    private final TrainerWithTraineesMapper trainerWithTraineesMapper;

    private final GymReportsClient gymReportsClient;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserCredentialsDTO createTrainer(TrainerDTO trainerDTO) {
        log.info("Entry TrainerServiceImpl createTrainer ");

        String generatedPassword = profileService.generatePassword();

        User user = User.builder()
                .firstName(trainerDTO.getFirstName())
                .lastName(trainerDTO.getLastName())
                .userName(profileService.generateUsername(trainerDTO.getFirstName(), trainerDTO.getLastName()))
                .password(passwordEncoder.encode(generatedPassword))
                .isActive(false)
                .build();

        Trainer trainerToSave = Trainer.builder()
                .user(user)
                .specialization(trainingTypeService.findTrainingTypeByName(trainerDTO.getSpecialization().getTrainingTypeName()))
                .trainees(new HashSet<>())
                .build();

        trainerRepository.save(trainerToSave);

        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setUsername(user.getUserName());
        userCredentialsDTO.setPassword(generatedPassword);

        log.info("Exit TrainerServiceImpl createTrainer Trainer: {}", user.getUserName());
        return userCredentialsDTO;
    }

    @Override
    @Transactional
    public TrainerWithTraineesDTO updateTrainer(TrainerDTO trainerDTO) {
        log.info("Enter TrainerServiceImpl updateTrainer trainer: {}", trainerDTO.getUserName());

        Trainer trainer = trainerRepository.getTrainerByUser_UserName(trainerDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException("Trainer with username " + trainerDTO.getUserName() + " not found"));

        TrainingType trainingTypeByNameToUpdate = trainingTypeService.findTrainingTypeByName(trainerDTO.getSpecialization().getTrainingTypeName());

        trainerWithTraineesMapper.updateTrainerFromDTO(trainerDTO, trainer);
        trainer.setSpecialization(trainingTypeByNameToUpdate);

        Trainer updated = trainerRepository.save(trainer);

        log.info("Exit TrainerServiceImpl updateTrainer trainer: {}", trainerDTO.getUserName());
        return trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerWithTraineesDTO getTrainerDTOByUserName(String userName) {
        log.info("Enter TrainerServiceImpl getTrainerDTOByUserName trainer: {}", userName);

        Trainer trainer = trainerRepository.getTrainerByUser_UserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Trainer with userName " + userName + " not found"));

        log.info("Exit TrainerServiceImpl getTrainerDTOByUserName trainer: {}", userName);
        return trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(trainer);
    }

    @Override
    @Transactional(readOnly = true)
    public Trainer getTrainerByUserName(String userName) {
        log.info("Enter TrainerServiceImpl getTrainerByUserName trainer: {}", userName);

        Trainer trainer = trainerRepository.getTrainerByUser_UserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Trainer with userName " + userName + " not found"));

        log.info("Exit TrainerServiceImpl getTrainerByUserName trainer: {}", userName);
        return trainer;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trainer> getTrainersByUsernameList(List<String> trainerUsernames) {
        log.info("Enter TrainerServiceImpl getTrainersByUsernameList method: {}", trainerUsernames);

        List<Trainer> trainersByUsernameList = trainerRepository.getTrainersByUser_UserNameIn(trainerUsernames);

        log.info("Exit TrainerServiceImpl getTrainersByUsernameList method: {}", trainerUsernames);
        return trainersByUsernameList;
    }

    @Override
    public Integer getTrainerSummaryByUsername(String username, int year, int monthValue, String authHeader) {
        log.info("Entry TrainerServiceImpl getTrainerSummaryByUsername method");

        Integer trainerSummaryByUsername = gymReportsClient.getTrainerSummaryByUsername(username, year, monthValue, authHeader);

        log.info("Entry TrainerServiceImpl getTrainerSummaryByUsername method");
        return trainerSummaryByUsername;
    }
}
