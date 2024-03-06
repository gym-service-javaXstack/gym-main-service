package com.example.springcore.repository;

import com.example.springcore.model.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Training training) {
        entityManager.persist(training);
    }
}
