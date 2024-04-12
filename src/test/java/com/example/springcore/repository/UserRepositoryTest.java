package com.example.springcore.repository;

import com.example.springcore.model.User;
import com.example.springcore.repository.util.RepositoryTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user1;
    private User user2;

    @BeforeEach
    void setup() {
        user1 = RepositoryTestUtil.createUserAndPersist(entityManager, "JohnDoe", true, "John", "Doe", "password");
        user2 = RepositoryTestUtil.createUserAndPersist(entityManager, "JaneDoe", true, "Jane", "Doe", "password");
    }

    @Test
    void UserRepository_getUserNamesByFirstNameAndLastName_ReturnListOfString() {
        //Arrange
        String baseUserName = "John";

        //Act
        List<String> foundUserNames = userRepository.getUserNamesByFirstNameAndLastName(baseUserName);

        //Assert
        assertThat(foundUserNames, hasSize(1));
        assertThat(foundUserNames.get(0), equalTo(user1.getUserName()));
    }

    @Test
    void UserRepository_getUserByUserName_ReturnOptionalOfUser() {
        //Arrange
        String testUserName = "JaneDoe";

        //Act
        Optional<User> foundUser = userRepository.getUserByUserName(testUserName);

        //Assert
        assertTrue(foundUser.isPresent());
        assertEquals(user2.getUserName(), foundUser.get().getUserName());
    }
}