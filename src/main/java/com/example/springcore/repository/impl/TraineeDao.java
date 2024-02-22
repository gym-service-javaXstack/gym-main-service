package com.example.springcore.repository.impl;

import com.example.springcore.model.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TraineeDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Trainee save(Trainee trainee) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Trainee savedTrainee;
        try {
            tx = session.beginTransaction();
            session.persist(trainee);
            savedTrainee = trainee;
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
        return savedTrainee;
    }

    public Optional<Trainee> get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Trainee.class, id));
    }

    public List<Trainee> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Trainee", Trainee.class).getResultList();
    }

    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Trainee trainee = session.get(Trainee.class, id);
        if (trainee != null) {
            session.delete(trainee);
        }
    }
}
