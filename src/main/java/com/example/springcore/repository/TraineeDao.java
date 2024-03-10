package com.example.springcore.repository;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TraineeDao extends UserDao<Trainee> {

    @PersistenceContext
    private EntityManager entityManager;

    public void delete(Trainee trainee) {
        entityManager.remove(entityManager.contains(trainee) ? trainee : entityManager.merge(trainee));
    }

    public Optional<Trainee> getTraineeByUsername(String username) {
        return Optional.of(entityManager.createQuery(
                        "select t from Trainee t " +
                                "join fetch t.user u " +
                                "left join fetch t.trainers tr " +
                                "left join fetch tr.specialization " +
                                "left join fetch tr.user " +
                                "where u.userName = :username",
                        Trainee.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    public void linkTraineeAndTrainee(Trainee trainee, Trainer trainer) {
        if (!trainee.getTrainers().contains(trainer)) {
            trainee.addTrainer(trainer);
            entityManager.merge(trainee);
        }
    }

    public List<Trainer> getTrainersNotAssignedToTrainee(String username) {
        return entityManager.createQuery(
                        "select t from Trainer t " +
                                "join fetch t.user ttu " +
                                "join fetch t.specialization " +
                                "left join fetch t.trainees tt " +
                                "left join fetch tt.user traineename " +
                                "where (traineename.userName !=:username or tt.id is null) and ttu.isActive = true",
                        Trainer.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerUserName, String trainingTypeName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = cb.createQuery(Training.class);
        Root<Training> training = query.from(Training.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(training.get("trainee").get("user").get("userName"), username));

        if (fromDate != null && toDate != null) {
            predicates.add(cb.between(training.get("trainingDate"), fromDate, toDate));
        }
        if (trainerUserName != null) {
            predicates.add(cb.equal(training.get("trainer").get("user").get("userName"), trainerUserName));
        }
        if (trainingTypeName != null) {
            predicates.add(cb.equal(training.get("trainingType").get("trainingTypeName"), trainingTypeName));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
