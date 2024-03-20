package com.example.springcore.repository;

import com.example.springcore.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
    TrainingType findTrainingTypeByTrainingTypeName(String name);
}
