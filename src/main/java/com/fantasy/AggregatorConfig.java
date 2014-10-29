/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy;

import com.fantasy.stataggregator.entities.GameData;
import com.fantasy.stataggregator.entities.dao.impl.GameDataRepository;
import com.fantasy.stataggregator.entities.dao.impl.ScheduleRepository;
import com.fantasy.stataggregator.workers.JsonRetreiver;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import java.util.Properties;
import java.util.Timer;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.eclipse.persistence.config.EntityManagerProperties;

/**
 *
 * @author MacDerson
 */

/**
 * to use the annotationConfig use ApplicationContext ctx = new
 * AnnotationConfigApplicationContext(AggregatorConfig.class)
 *
 * SomeClass sc = ctx.getBean(SomeClass.class);
 */
@Configuration()
public class AggregatorConfig {

//    @Bean
//    @Primary
//    public EntityManagerFactory entityManagerFactory() {
//        return Persistence.createEntityManagerFactory("com.fantasy_StatAggregator_jar_1.0PU");
//    }

    @Bean
    public ScheduleRepository scheduleRepository() {
        return new ScheduleRepository();
    }

    @Bean
    public GameDataRepository gameDataRepository() {
        return new GameDataRepository();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GameData gameData() {
        return new GameData();
    }

    @Bean
    public ClientConfig clientConfig() {
        ClientConfig cfg = new ClientConfig();
        cfg.register(JacksonJsonProvider.class);
        return cfg;
    }

    @Bean
    public Client client() {
        return ClientBuilder.newClient(clientConfig());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Timer timer() {
        return new Timer();
    }

    @Bean
    public JsonRetreiver jsonRetriever() {
        return new JsonRetreiver();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"com.fantasy.stataggregator.entities"});

        JpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/fantasy");
        dataSource.setUsername("postgres");
        dataSource.setPassword("notorious");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        //properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        //properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty(EntityManagerProperties.PERSISTENCE_CONTEXT_PERSIST_ON_COMMIT, "true");
        return properties;
    }
}
