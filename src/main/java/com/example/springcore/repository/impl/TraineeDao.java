package com.example.springcore.repository.impl;

import com.example.springcore.model.Trainee;
import com.example.springcore.repository.ParentUserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TraineeDao extends ParentUserDao<Trainee> {

    protected TraineeDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void delete(String username) {
        Session session = sessionFactory.getCurrentSession();
        Optional<Trainee> userByUsername = super.getUserByUsername(username, user -> user.getTrainee());
        userByUsername.ifPresent(session::remove);
    }
}

