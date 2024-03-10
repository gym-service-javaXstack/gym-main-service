package com.example.springcore.repository;

import com.example.springcore.model.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    public TrainingType findTrainingTypeByName(String name) {
        return entityManager.createQuery("select t from TrainingType t where t.trainingTypeName = :name",
                TrainingType.class).setParameter("name", name).getSingleResult();
    }

    public List<TrainingType> getAllTrainingTypes() {
        return entityManager.createQuery(
                "select t from TrainingType t",
                        TrainingType.class)
                .getResultList();
    }
}
