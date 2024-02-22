package com.example.springcore.config;

import com.example.springcore.model.Trainee;
import com.example.springcore.model.Trainer;
import com.example.springcore.model.Training;
import com.example.springcore.model.TrainingType;
import com.example.springcore.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class HibernateConfig {

    @Value("${db.driver}")
    private String DRIVER;

    @Value("${db.password}")
    private String PASSWORD;

    @Value("${db.url}")
    private String URL;

    @Value("${db.username}")
    private String USERNAME;

    @Value("${hibernate.show_sql}")
    private String SHOW_SQL;

    @Value("${hibernate.hbm2ddl.auto}")
    private String CREATE_DROP;

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
        config.setProperties(hibernateProperties());

        config.addAnnotatedClass(User.class);
        config.addAnnotatedClass(TrainingType.class);
        config.addAnnotatedClass(Trainee.class);
        config.addAnnotatedClass(Trainer.class);
        config.addAnnotatedClass(Training.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateProperties()).build();

        return config.buildSessionFactory(serviceRegistry);
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", DRIVER);
        properties.put("hibernate.connection.url", URL);
        properties.put("hibernate.connection.username", USERNAME);
        properties.put("hibernate.connection.password", PASSWORD);
        properties.put("hibernate.show_sql", SHOW_SQL);
        properties.put("hibernate.hbm2ddl.auto", CREATE_DROP);
        return properties;
    }
}