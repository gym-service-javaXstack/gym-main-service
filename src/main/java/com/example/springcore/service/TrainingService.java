package com.example.springcore.service;

import com.example.springcore.dto.TrainingDTO;
import com.example.springcore.mapper.TraineeTrainingMapper;
import com.example.springcore.mapper.TrainerTrainingMapper;
import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.repository.TrainingRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TrainingService {
    private final TrainingRepository trainingRepository;

    private final TraineeService traineeService;
    private final TrainerService trainerService;

    private final TrainerTrainingMapper trainerTrainingMapper;
    private final TraineeTrainingMapper traineeTrainingMapper;

    private final Counter trainingCounter;
    private final Timer trainingCreationTimer;

    public TrainingService(TrainingRepository trainingRepository,
                           TraineeService traineeService,
                           TrainerService trainerService,
                           TrainerTrainingMapper trainerTrainingMapper,
                           TraineeTrainingMapper traineeTrainingMapper,
                           MeterRegistry meterRegistry) {
        this.trainingRepository = trainingRepository;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainerTrainingMapper = trainerTrainingMapper;
        this.traineeTrainingMapper = traineeTrainingMapper;

        this.trainingCounter = Counter.builder("training.creation.count")
                .description("The counter that counts the number of calls to the createTraining method")
                .register(meterRegistry);
        this.trainingCreationTimer = Timer.builder("training.creation.time")
                .description("The timer describing the time of creating a training")
                .register(meterRegistry);
    }

    @Transactional
    public void createTraining(TrainingDTO trainingDTO) {
        log.info("Enter TrainingService createTraining");

        // Prometheus Start the timer
        Timer.Sample sample = Timer.start();

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

        // Prometheus Increment the counter
        trainingCounter.increment();

        // Prometheus Stop the timer and record the time
        sample.stop(trainingCreationTimer);

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


        return trainerTrainingMapper.fromTrainingListToTrainerTrainingListDTO(trainingRepository.findAll(
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

                    log.info("Exit TrainerService getTrainerTrainingsByCriteria method: {}", username);
                    return cb.and(predicates.toArray(new Predicate[0]));
                }
        ));
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

        return traineeTrainingMapper.fromTrainingListToTraineeTrainingListDTO(trainingRepository.findAll(
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

                    log.info("Exit TraineeService getTraineeTrainingsByCriteria method: {}", username);
                    return cb.and(predicates.toArray(new Predicate[0]));
                }
        ));
    }
}
