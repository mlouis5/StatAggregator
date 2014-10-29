/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator;

import com.fantasy.stataggregator.entities.dao.impl.ScheduleRepository;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
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
    
    @Bean()
    public EntityManagerFactory entityManagerFactory(){
        return Persistence.createEntityManagerFactory("com.fantasy_StatAggregator_jar_1.0PU");
    }
    
    @Bean
    public ScheduleRepository scheduleRepository(){
        return new ScheduleRepository(entityManagerFactory());
    }
}
