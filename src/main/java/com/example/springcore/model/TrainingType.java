package com.example.springcore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Builder
@Table(name = "training_type")
@NoArgsConstructor
@AllArgsConstructor
public class TrainingType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "training_type_name", nullable = false)
    private String trainingTypeName;

    @OneToMany(mappedBy = "specialization")
    @ToString.Exclude
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "trainingType")
    @ToString.Exclude
    private List<Training> trainings;
}
