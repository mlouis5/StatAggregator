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
import java.util.Timer;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
/**
 *
 * @author MacDerson
 */

/**
 * to use the annotationConfig use 
 * ApplicationContext ctx = new AnnotationConfigApplicationContext(AggregatorConfig.class)
 * 
 * SomeClass sc = ctx.getBean(SomeClass.class);
 */
@Configuration()
public class AggregatorConfig {
    
    @Bean
    @Primary
    public EntityManagerFactory entityManagerFactory(){
        return Persistence.createEntityManagerFactory("com.fantasy_StatAggregator_jar_1.0PU");
    }
    
    @Bean
    public ScheduleRepository scheduleRepository(){
        return new ScheduleRepository(entityManagerFactory());
    }
    
    @Bean
    public GameDataRepository gameDataRepository(){
        return new GameDataRepository(entityManagerFactory());
    }
    
    @Bean
    @Scope(value= ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public GameData gameData(){
        return new GameData();
    }
    
    @Bean
    public ClientConfig clientConfig(){
        ClientConfig cfg = new ClientConfig();
        cfg.register(JacksonJsonProvider.class);
        return cfg;
    }
    
    @Bean
    public Client client(){
        return ClientBuilder.newClient(clientConfig());
    }  
    
    @Bean
    @Scope(value= ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Timer timer(){
        return new Timer();
    }
    
    @Bean
    public JsonRetreiver jsonRetriever(){
        return new JsonRetreiver();
    }
}
