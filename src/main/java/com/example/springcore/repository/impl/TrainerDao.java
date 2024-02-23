package com.example.springcore.repository.impl;

import com.example.springcore.model.Trainer;
import com.example.springcore.repository.ParentUserDao;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerDao extends ParentUserDao<Trainer> {
    protected TrainerDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
