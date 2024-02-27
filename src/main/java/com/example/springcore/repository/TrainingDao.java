package com.example.springcore.repository;

import com.example.springcore.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingDao {
    private final SessionFactory sessionFactory;

    public TrainingDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(training);
    }
}
