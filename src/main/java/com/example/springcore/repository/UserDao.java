package com.example.springcore.repository;

import com.example.springcore.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getUsernamesByFirstNameAndLastName(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        return entityManager.createQuery("select u.userName from User u where u.userName like :baseUsername", String.class)
                .setParameter("baseUsername", baseUsername + "%")
                .getResultList();
    }

    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    public Optional<User> getUserByUsername(String username) {
        return Optional.of(entityManager.createQuery("from User u where u.userName = :username", User.class)
                .setParameter("username", username)
                .getSingleResult());

    }
}
