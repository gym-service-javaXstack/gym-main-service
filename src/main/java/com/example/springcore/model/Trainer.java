package com.example.springcore.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Trainer extends User{
    Integer userId;
    TrainingType specialization;
}
