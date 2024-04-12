package com.example.springcore.repository;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    @Query("select t from Trainee t " +
            "join fetch t.user u " +
            "left join fetch t.trainers tr " +
            "left join fetch tr.specialization " +
            "left join fetch tr.user " +
            "where u.userName = :userName")
    Optional<Trainee> getTraineeByUser_UserName(@Param("userName") String userName);

    @Query("select t from Trainer t " +
            "join fetch t.user ttu " +
            "join fetch t.specialization " +
            "left join fetch t.trainees tt " +
            "left join fetch tt.user traineename " +
            "where (traineename.userName != :username or tt.id is null) and ttu.isActive = true")
    List<Trainer> getTrainersNotAssignedToTrainee(@Param("username") String username);
}
