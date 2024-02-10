package com.example.springcore.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Trainee extends User{
    Integer userId;
    String address;
    LocalDate dateOfBirth;

    public Trainee(String firstName, String lastName, String userName, String password, Boolean isActive, Integer userId, String address, LocalDate dateOfBirth) {
        super(firstName, lastName, userName, password, isActive);
        this.userId = userId;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
}
