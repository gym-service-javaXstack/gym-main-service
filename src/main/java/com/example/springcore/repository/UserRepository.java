package com.example.springcore.repository;

import com.example.springcore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.userName from User u where u.userName like :baseUserName%")
    List<String> getUserNamesByFirstNameAndLastName(@Param("baseUserName") String baseUserName);

    Optional<User> getUserByUserName(String userName);
}
