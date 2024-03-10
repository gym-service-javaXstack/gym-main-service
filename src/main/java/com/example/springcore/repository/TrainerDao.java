package com.example.springcore.repository;

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
public class TrainerDao extends UserDao<Trainer> {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Trainer> getTrainerByUsername(String username) {
        return Optional.of(entityManager.createQuery(
                        "select t from Trainer t " +
                                "join fetch t.user u " +
                                "join fetch t.specialization " +
                                "left join fetch t.trainees tn " +
                                "left join fetch tn.user " +
                                "where u.userName = :username",
                        Trainer.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    public List<Training> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeUserName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Training> query = cb.createQuery(Training.class);
        Root<Training> training = query.from(Training.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(training.get("trainer").get("user").get("userName"), username));

        if (fromDate != null && toDate != null) {
            predicates.add(cb.between(training.get("trainingDate"), fromDate, toDate));
        }
        if (traineeUserName != null) {
            predicates.add(cb.equal(training.get("trainee").get("user").get("userName"), traineeUserName));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Trainer> getTrainersByUsernameList(List<String> trainerUsernames) {
        return entityManager.createQuery(
                        "select t from Trainer t " +
                                "join fetch t.user u " +
                                "where u.userName in :usernames", Trainer.class)
                .setParameter("usernames", trainerUsernames)
                .getResultList();
    }
}
