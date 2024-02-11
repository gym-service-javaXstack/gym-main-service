package com.example.springcore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Trainee extends User{
    Integer userId;
    String address;
    LocalDate dateOfBirth;
}
