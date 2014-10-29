/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy;

import com.fantasy.stataggregator.workers.JsonRetreiver;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Mac
 */
public class Application {
    
    public static void main(String[] args){
        try (ConfigurableApplicationContext context = SpringApplication.run(AggregatorConfig.class)) {
            Timer timer = context.getBean(Timer.class);
            TimerTask jsonRetriever = context.getBean(JsonRetreiver.class);
            
            
            timer.scheduleAtFixedRate(jsonRetriever, 0, 1000);
        }
    }
}
