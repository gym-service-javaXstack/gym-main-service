package com.example.springcore.repository.impl;

import com.example.springcore.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainingDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Training save(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(training);
        return training;
    }

    public Optional<Training> get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Training.class, id));
    }

    public List<Training> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Training", Training.class).getResultList();
    }
}
