package com.example.springcore.repository;

import com.example.springcore.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    @Query("select t from Trainer t " +
            "join fetch t.user u " +
            "join fetch t.specialization " +
            "left join fetch t.trainees tn " +
            "left join fetch tn.user " +
            "where u.userName = :userName")
    Optional<Trainer> getTrainerByUser_UserName(@Param("userName") String userName);

    @Query("select t from Trainer t " +
            "join fetch t.user u " +
            "where u.userName in :usernames")
    List<Trainer> getTrainersByUser_UserNameIn(@Param("usernames") List<String> usernames);

}
