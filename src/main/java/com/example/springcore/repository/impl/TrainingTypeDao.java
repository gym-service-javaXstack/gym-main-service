package com.example.springcore.repository.impl;

import com.example.springcore.model.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TrainingTypeDao {
    private final SessionFactory sessionFactory;

    public TrainingTypeDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public TrainingType findTrainingTypeByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<TrainingType> query = session.createQuery(
                "select t from TrainingType t where t.trainingTypeName = :name",
                TrainingType.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }
}
