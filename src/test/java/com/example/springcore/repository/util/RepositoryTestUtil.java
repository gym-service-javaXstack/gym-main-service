package com.example.springcore.repository.util;

import com.example.springcore.model.User;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class RepositoryTestUtil {

    public static User createUserAndPersist(TestEntityManager entityManager, String userName, boolean isActive, String firstName, String lastName, String password) {
        User user = createUser(userName, isActive, firstName, lastName, password);
        persistUser(entityManager, user);
        return user;
    }

    public static User createUser(String userName, boolean isActive, String firstName, String lastName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setIsActive(isActive);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        return user;
    }

    public static void persistUser(TestEntityManager entityManager, User user) {
        entityManager.persist(user);
        entityManager.flush();
    }
}
