package com.example.springcore.repository;

import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainerDao extends UserDao<Trainer> {
    protected TrainerDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<Trainer> getTrainerByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                "select t from Trainer t " +
                        "join fetch t.user u " +
                        "join fetch t.specialization " +
                        "left join fetch t.trainees tn " +
                        "left join fetch tn.user " +
                        "where u.userName = :username",
                        Trainer.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }

    public List<Training> getTrainerTrainingsByCriteria(String username, LocalDate fromDate, LocalDate toDate, String traineeName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
                        "select tr from Training tr " +
                                "join tr.trainer t " +
                                "join t.user u " +
                                "join tr.trainee trn " +
                                "join trn.user trnUser " +
                                "where u.userName = :username " +
                                "and (tr.trainingDate between :fromDate and :toDate) " +
                                "and trnUser.firstName = :traineeName",
                        Training.class)
                .setParameter("username", username)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("traineeName", traineeName)
                .getResultList();
    }
}
