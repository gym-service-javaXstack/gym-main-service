package com.example.springcore.service.impl;

import com.example.springcore.dto.ActionType;
import com.example.springcore.dto.TrainerWorkLoadRequest;
import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.repository.TrainingRepository;
import com.example.springcore.service.GymReportsService;
import com.example.springcore.service.TraineeService;
import com.example.springcore.service.TrainerService;
import com.example.springcore.service.TrainingService;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    private final GymReportsService gymReportsService;


    @Override
    @Transactional
    public void createTraining(TrainingDTO trainingDTO) {
        log.info("Enter TrainingServiceImpl createTraining");

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
        TrainerWorkLoadRequest trainerWorkLoadRequest = mapFromTrainingDTOAndTrainer(trainingDTO, trainerByUsername);
        log.info("TrainingServiceImpl createTraining TrainerWorkLoadRequest {}", trainerWorkLoadRequest);
        gymReportsService.processTrainerWorkload(trainerWorkLoadRequest);
        log.info("Exit TrainingServiceImpl createTraining: {}", training);
    }

    private TrainerWorkLoadRequest mapFromTrainingDTOAndTrainer(TrainingDTO trainingDTO, Trainer trainer) {
        return TrainerWorkLoadRequest
                .builder()
                .actionType(ActionType.ADD)
                .firstName(trainer.getUser().getFirstName())
                .lastName(trainer.getUser().getLastName())
                .isActive(trainer.getUser().getIsActive())
                .username(trainingDTO.getTrainerUserName())
                .trainingDate(trainingDTO.getTrainingDate())
                .trainingDuration(trainingDTO.getDuration())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getTrainerTrainingsByCriteria(
            String username,
            LocalDate fromDate,
            LocalDate toDate,
            String traineeUserName
    ) {
        log.info("Enter TrainerServiceImpl getTrainerTrainingsByCriteria method: {}", username);

        return trainingRepository.findAll(
                (root, query, cb) -> {
                    root.fetch("trainer", JoinType.LEFT).fetch("user", JoinType.LEFT);
                    root.fetch("trainee", JoinType.LEFT).fetch("user", JoinType.LEFT);
                    root.fetch("trainingType", JoinType.LEFT);

                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(cb.equal(root.get("trainer").get("user").get("userName"), username));

                    if (fromDate != null && toDate != null) {
                        predicates.add(cb.between(root.get("trainingDate"), fromDate, toDate));
                    }
                    if (traineeUserName != null) {
                        predicates.add(cb.equal(root.get("trainee").get("user").get("userName"), traineeUserName));
                    }

                    log.info("Exit TrainerServiceImpl getTrainerTrainingsByCriteria method: {}", username);
                    return cb.and(predicates.toArray(new Predicate[0]));
                }
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainingsByCriteria(
            String username,
            LocalDate fromDate,
            LocalDate toDate,
            String trainerUsername,
            String trainingTypeName
    ) {
        log.info("Enter TraineeServiceImpl getTraineeTrainingsByCriteria method: {}", username);

        return trainingRepository.findAll(
                (root, query, cb) -> {
                    root.fetch("trainee", JoinType.LEFT).fetch("user", JoinType.LEFT);
                    root.fetch("trainer", JoinType.LEFT).fetch("user", JoinType.LEFT);
                    root.fetch("trainingType", JoinType.LEFT);

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

                    log.info("Exit TraineeServiceImpl getTraineeTrainingsByCriteria method: {}", username);
                    return cb.and(predicates.toArray(new Predicate[0]));
                }
        );
    }
}
