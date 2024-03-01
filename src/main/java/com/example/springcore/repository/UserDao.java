package com.example.springcore.repository;

import com.example.springcore.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao<T> {
    protected SessionFactory sessionFactory;

    protected UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<String> getUsernamesByFirstNameAndLastName(String firstName, String lastName) {
        Session session = sessionFactory.getCurrentSession();
        String baseUsername = firstName + "." + lastName;
        return session.createQuery("select u.userName from User u where u.userName like :baseUsername", String.class)
                .setParameter("baseUsername", baseUsername + "%")
                .getResultList();
    }

    public T save(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;
    }

    public T update(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(entity);
        return entity;
    }

    public Optional<User> getUserByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User u where u.userName = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional();
    }
}
