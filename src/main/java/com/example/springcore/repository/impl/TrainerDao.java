package com.example.springcore.repository.impl;

import com.example.springcore.model.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Trainer save(Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(trainer);
        return trainer;
    }

    public Optional<Trainer> get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Trainer.class, id));
    }

    public List<Trainer> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Trainer", Trainer.class).getResultList();
    }
}
