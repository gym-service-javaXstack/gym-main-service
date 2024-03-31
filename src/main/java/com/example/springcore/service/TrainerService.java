package com.example.springcore.service;

import com.example.springcore.dto.TrainerDTO;
import com.example.springcore.dto.TrainerWithTraineesDTO;
import com.example.springcore.dto.UserCredentialsDTO;
import com.example.springcore.mapper.TrainerWithTraineesMapper;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import com.example.springcore.repository.TrainerRepository;
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
public class TrainerService {
    private final TrainerRepository trainerRepository;

    private final ProfileService profileService;
    private final TrainingTypeService trainingTypeService;

    private final TrainerWithTraineesMapper trainerWithTraineesMapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserCredentialsDTO createTrainer(TrainerDTO trainerDTO) {
        log.info("Entry TrainerService createTrainer ");

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

        log.info("Exit TrainerService createTrainer Trainer: {}", user.getUserName());
        return userCredentialsDTO;
    }

    @Transactional
    public TrainerWithTraineesDTO updateTrainer(TrainerDTO trainerDTO) {
        log.info("Enter TrainerService updateTrainer trainer: {}", trainerDTO.getUserName());

        Trainer trainer = trainerRepository.getTrainerByUser_UserName(trainerDTO.getUserName())
                .orElseThrow(() -> new EntityNotFoundException("Trainer with username " + trainerDTO.getUserName() + " not found"));

        TrainingType trainingTypeByNameToUpdate = trainingTypeService.findTrainingTypeByName(trainerDTO.getSpecialization().getTrainingTypeName());

        trainerWithTraineesMapper.updateTrainerFromDTO(trainerDTO, trainer);
        trainer.setSpecialization(trainingTypeByNameToUpdate);

        Trainer updated = trainerRepository.save(trainer);

        log.info("Exit TrainerService updateTrainer trainer: {}", trainerDTO.getUserName());
        return trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(updated);
    }

    @Transactional(readOnly = true)
    public TrainerWithTraineesDTO getTrainerDTOByUserName(String userName) {
        log.info("Enter TrainerService getTrainerDTOByUserName trainer: {}", userName);

        Trainer trainer = trainerRepository.getTrainerByUser_UserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Trainer with userName " + userName + " not found"));

        log.info("Exit TrainerService getTrainerDTOByUserName trainer: {}", userName);
        return trainerWithTraineesMapper.fromTrainerToTrainerWithTraineesDTO(trainer);
    }

    @Transactional(readOnly = true)
    public Trainer getTrainerByUserName(String userName) {
        log.info("Enter TrainerService getTrainerByUserName trainer: {}", userName);

        Trainer trainer = trainerRepository.getTrainerByUser_UserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Trainer with userName " + userName + " not found"));

        log.info("Exit TrainerService getTrainerByUserName trainer: {}", userName);
        return trainer;
    }

    @Transactional(readOnly = true)
    public List<Trainer> getTrainersByUsernameList(List<String> trainerUsernames) {
        log.info("Enter TrainerService getTrainersByUsernameList method: {}", trainerUsernames);

        List<Trainer> trainersByUsernameList = trainerRepository.getTrainersByUser_UserNameIn(trainerUsernames);

        log.info("Exit TrainerService getTrainersByUsernameList method: {}", trainerUsernames);
        return trainersByUsernameList;
    }
}
