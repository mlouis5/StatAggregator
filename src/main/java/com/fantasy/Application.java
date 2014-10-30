/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy;

import com.fantasy.stataggregator.Task;
import com.fantasy.stataggregator.workers.GameDataRetrieverTask;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Mac
 */
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context;
        context = SpringApplication.run(AggregatorConfig.class);
        Task jsonRetreiver = context.getBean(GameDataRetrieverTask.class);
        ((GameDataRetrieverTask) jsonRetreiver).setSearchYear(2013);
        try {
            while (!jsonRetreiver.taskComplete()) {
                jsonRetreiver.run();
                Thread.sleep(5000L);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (Objects.nonNull(context)) {
                context.close();
            }
        }
    }
}
