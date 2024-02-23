package com.example.springcore.repository.impl;

import com.example.springcore.model.Trainee;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TraineeDao {
    private SessionFactory sessionFactory;

    @Transactional
    public Trainee save(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(trainee);
        return trainee;
    }

    @Transactional(readOnly = true)
    public Optional<Trainee> get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Trainee.class, id));
    }

    @Transactional(readOnly = true)
    public List<Trainee> getAll() {
        Session session = sessionFactory.getCurrentSession();
        List<Trainee> trainees = session.createQuery("from Trainee t left join fetch t.trainers", Trainee.class).getResultList();
        return trainees;
    }

    @Transactional
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Optional<Trainee> trainee = Optional.ofNullable(session.get(Trainee.class, id));
        trainee.ifPresent(session::remove);
    }
}
