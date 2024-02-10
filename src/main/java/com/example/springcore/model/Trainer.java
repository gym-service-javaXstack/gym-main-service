package com.example.springcore.model;

import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Trainer extends User{
    Integer userId;
    TrainingType specialization;

    public Trainer(String firstName, String lastName, String userName, String password, Boolean isActive, Integer userId, TrainingType specialization) {
        super(firstName, lastName, userName, password, isActive);
        this.userId = userId;
        this.specialization = specialization;
    }

    public Trainer(String firstName, String lastName, Boolean isActive, Integer userId, TrainingType specialization) {
        super(firstName, lastName, isActive);
        this.userId = userId;
        this.specialization = specialization;
    }
}
