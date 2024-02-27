package com.example.springcore.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "trainee")
@SuperBuilder
@ToString(exclude = {"trainings"})
@AllArgsConstructor
@NoArgsConstructor
public class Trainee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "address", nullable = false)
    String address;

    @Column(name = "date_of_birth", nullable = false)
    LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id", referencedColumnName = "id", unique = true)
    private User user;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "trainee_trainer",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"trainee_id", "trainer_id"}))
    private Set<Trainer> trainers = new HashSet<>();

    @OneToMany(mappedBy = "trainee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Training> trainings;

    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
        trainer.getTrainees().add(this);
    }

    public void removeTrainer(Trainer trainer) {
        trainers.remove(trainer);
        trainer.getTrainees().remove(this);
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", user=" + user +
                ", trainers=" + (trainers.isEmpty() ? "not initialized" : trainers.stream()
                .map(trainer -> "Trainer{user=" + trainer.getUser() + ", specialization=" + trainer.getSpecialization() + "}")
                .collect(Collectors.joining(", "))) +
                '}';
    }
}
