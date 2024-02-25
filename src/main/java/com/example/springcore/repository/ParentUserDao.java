package com.example.springcore.repository;

import com.example.springcore.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class ParentUserDao<T> {
    protected SessionFactory sessionFactory;

    protected ParentUserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<String> getUsernamesByFirstNameAndLastName(String firstName, String lastName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u.userName from User u where u.firstName = :firstName and u.lastName = :lastName", String.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
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

    public T changePassword(String username, String newPassword, Function<User, T> converter) {
        Session session = sessionFactory.getCurrentSession();
        Optional<User> userOptional = session.createQuery("from User u left join fetch u.trainee left join fetch  u.trainer t left join fetch t.specialization where u.userName = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword);
            session.merge(user);
            return converter.apply(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public Optional<T> getUserByUsername(String username, Function<User, T> converter) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from User u left join fetch u.trainee left join fetch u.trainer t left join fetch t.specialization where u.userName = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional()
                .map(converter);
    }

    public void changeUserStatus(String username, boolean isActive) {
        Session session = sessionFactory.getCurrentSession();
        Optional<User> userOptional = session.createQuery("from User u where u.userName = :username", User.class)
                .setParameter("username", username)
                .uniqueResultOptional();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsActive(isActive);
            session.merge(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
