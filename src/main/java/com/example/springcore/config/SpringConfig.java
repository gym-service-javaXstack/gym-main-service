package com.example.springcore.config;

import com.example.springcore.interceptor.LogInterceptor;
import com.example.springcore.interceptor.TransactionLoggerInterceptor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.configuration.SpringDocUIConfiguration;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class SpringConfig implements WebMvcConfigurer {

    private final TransactionLoggerInterceptor transactionLoggerInterceptor;
    private final LogInterceptor logInterceptor;

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

    @Value("${hibernate.format_sql}")
    private String FORMAT_SQL;

    @Value("${hibernate.use_sql_comments}")
    private String USE_SQL_COMMENTS;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("com.example.springcore.model")
                .addProperties(hibernateProperties());
        return builder.buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", SHOW_SQL);
        properties.put("hibernate.hbm2ddl.auto", CREATE_DROP);
        properties.put("hibernate.format_sql", FORMAT_SQL);
        properties.put("hibernate.use_sql_comments", USE_SQL_COMMENTS);
        return properties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(transactionLoggerInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(logInterceptor).addPathPatterns("/api/**");
    }

    @Bean
    SpringDocConfiguration springDocConfiguration() {
        return new SpringDocConfiguration();
    }

    @Bean
    SpringDocConfigProperties springDocConfigProperties() {
        return new SpringDocConfigProperties();
    }

    @Bean
    ObjectMapperProvider objectMapperProvider(SpringDocConfigProperties springDocConfigProperties) {
        return new ObjectMapperProvider(springDocConfigProperties);
    }

    @Bean
    SpringDocUIConfiguration SpringDocUIConfiguration(Optional<SwaggerUiConfigProperties> optionalSwaggerUiConfigProperties) {
        return new SpringDocUIConfiguration(optionalSwaggerUiConfigProperties);
    }
}