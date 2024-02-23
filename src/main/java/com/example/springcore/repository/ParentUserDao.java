package com.example.springcore.repository;

import com.example.springcore.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ParentUserDao<T> {
    protected SessionFactory sessionFactory;

    protected ParentUserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<String> getUsernamesByFirstNameAndLastName(String firstName, String lastName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u.userName from User u where u.firstName = :firstName and u.lastName = :lastName", String.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Transactional
    public T save(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;
    }

    @Transactional(readOnly = true)
    public Optional<T> getUserByUsername(String username, Function<User, T> converter) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User u where u.userName = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional()
                .map(converter);
    }
}
