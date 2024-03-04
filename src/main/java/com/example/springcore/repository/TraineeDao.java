package com.example.springcore.repository;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class TraineeDao extends UserDao<Trainee> {

    protected TraineeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void delete(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(trainee);
    }

    public Optional<Trainee> getTraineeByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                        "select t from Trainee t " +
                                "join fetch t.user u " +
                                "left join fetch t.trainers tr " +
                                "left join fetch tr.specialization " +
                                "left join fetch tr.user " +
                                "where u.userName = :username",
                        Trainee.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }

    public void updateTraineesTrainersList(Trainee trainee, Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        if (!trainee.getTrainers().contains(trainer)) {
            trainee.addTrainer(trainer);
            session.merge(trainee);
        }
    }

    public List<Trainer> getTrainersNotAssignedToTrainee(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                        "select t from Trainer t " +
                                "join fetch t.user ttu " +
                                "join fetch t.specialization " +
                                "left join fetch t.trainees tt " +
                                "join fetch tt.user " +
                                "where ttu.userName !=:username or tt.id is null",
                        Trainer.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Training> getTraineeTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String trainerName, TrainingType trainingType) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                        "select tr from Training tr " +
                                "join tr.trainee t " +
                                "join t.user u " +
                                "join tr.trainer trn " +
                                "join trn.user trnUser " +
                                "where u.userName = :username " +
                                "and (tr.trainingDate between :fromDate and :toDate) " +
                                "and trnUser.firstName = :trainerName " +
                                "and tr.trainingType = :trainingType",
                        Training.class)
                .setParameter("username", username)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("trainerName", trainerName)
                .setParameter("trainingType", trainingType)
                .getResultList();
    }
}
