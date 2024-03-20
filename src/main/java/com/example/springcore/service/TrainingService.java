package com.example.springcore.service;

import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.mapper.TraineeTrainingMapper;
import com.example.springcore.mapper.TrainerTrainingMapper;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.repository.TrainingRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final AuthenticationService authenticationService;

    private final TrainerTrainingMapper trainerTrainingMapper;
    private final TraineeTrainingMapper traineeTrainingMapper;

    @Transactional
    public void createTraining(TrainingDTO trainingDTO) {
        log.info("Enter TrainingService createTraining");

        Trainee traineeByUsername = traineeService.getTraineeByUsername(trainingDTO.getTraineeUserName());

        Trainer trainerByUsername = trainerService.getTrainerByUserName(trainingDTO.getTrainerUserName());

        traineeService.linkTraineeAndTrainee(traineeByUsername, trainerByUsername);

        Training training = Training.builder()
                .trainee(traineeByUsername)
                .trainer(trainerByUsername)
                .trainingName(trainingDTO.getTrainingName())
                .trainingType(trainerByUsername.getSpecialization())
                .trainingDate(trainingDTO.getTrainingDate())
                .duration(trainingDTO.getDuration())
                .build();

        trainingRepository.save(training);
        log.info("Exit TrainingService createTraining: {}", training);
    }

    @Transactional(readOnly = true)
    public List<TrainingDTO> getTrainerTrainingsByCriteria(
            String username,
            LocalDate fromDate,
            LocalDate toDate,
            String traineeUserName
    ) {
        log.info("Enter TrainerService getTrainerTrainingsByCriteria method: {}", username);

        authenticationService.isAuthenticated(username);

        return trainerTrainingMapper.fromTrainingListToTrainerTrainingListDTO(trainingRepository.findAll(
                        (root, query, cb) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            predicates.add(cb.equal(root.get("trainer").get("user").get("userName"), username));

                            if (fromDate != null && toDate != null) {
                                predicates.add(cb.between(root.get("trainingDate"), fromDate, toDate));
                            }
                            if (traineeUserName != null) {
                                predicates.add(cb.equal(root.get("trainee").get("user").get("userName"), traineeUserName));
                            }

                            log.info("Exit TrainerService getTrainerTrainingsByCriteria method: {}", username);
                            return cb.and(predicates.toArray(new Predicate[0]));
                        }
                )
        );
    }

    @Transactional(readOnly = true)
    public List<TrainingDTO> getTraineeTrainingsByCriteria(
            String username,
            LocalDate fromDate,
            LocalDate toDate,
            String trainerUsername,
            String trainingTypeName
    ) {
        log.info("Enter TraineeService getTraineeTrainingsByCriteria method: {}", username);

        authenticationService.isAuthenticated(username);

        return traineeTrainingMapper.fromTrainingListToTraineeTrainingListDTO(trainingRepository.findAll(
                (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(cb.equal(root.get("trainee").get("user").get("userName"), username));

                    if (fromDate != null && toDate != null) {
                        predicates.add(cb.between(root.get("trainingDate"), fromDate, toDate));
                    }
                    if (trainerUsername != null) {
                        predicates.add(cb.equal(root.get("trainer").get("user").get("userName"), trainerUsername));
                    }
                    if (trainingTypeName != null) {
                        predicates.add(cb.equal(root.get("trainingType").get("trainingTypeName"), trainingTypeName));
                    }

                    log.info("Exit TraineeService getTraineeTrainingsByCriteria method: {}", username);
                    return cb.and(predicates.toArray(new Predicate[0]));
                }
        ));
    }
}
