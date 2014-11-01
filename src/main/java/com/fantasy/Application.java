/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy;

import com.fantasy.stataggregator.Task;
import com.fantasy.stataggregator.workers.GameDataRetrieverTask;
import com.fantasy.utilities.containers.YearContainer;
import com.fantasy.utilities.flags.YearFlag;
import com.fantasy.utilities.parsers.SpaceDelimitedCommandLineParser;
import java.text.ParseException;
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

    public static void main(String[] args) throws ParseException, Exception {
        ConfigurableApplicationContext context;
        context = SpringApplication.run(AggregatorConfig.class);
        Task jsonRetreiver = context.getBean(GameDataRetrieverTask.class);
        
        SpaceDelimitedCommandLineParser<YearFlag, YearContainer> argParser;
        argParser = context.getBean(SpaceDelimitedCommandLineParser.class);
        YearContainer container = argParser.parseFor(YearFlag.class, args);
        
        //container.
        
        ((GameDataRetrieverTask) jsonRetreiver).setSearchYear(2014);
        try {
            while (!jsonRetreiver.taskComplete()) {
                jsonRetreiver.run();
                Thread.sleep(2000L);
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
