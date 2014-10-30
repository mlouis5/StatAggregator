/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.workers;

import com.fantasy.AggregatorConfig;
import com.fantasy.stataggregator.Task;
import com.fantasy.stataggregator.entities.GameData;
import com.fantasy.stataggregator.entities.GameDataPK;
import com.fantasy.stataggregator.entities.NflSchedule;
import com.fantasy.stataggregator.entities.dao.impl.GameDataRepository;
import com.fantasy.stataggregator.entities.dao.impl.ScheduleRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * NFLGameRetreiver retrieves every json file from nfl.com's game center for a given year<br>
 * then persist that data  as a LOB in a postgresql database.
 * @version %I%, %G%
 * @author MacDerson
 */
public class JsonRetreiver implements Task {

    private static final String pathSeparator = "/";
    private static final String BASE_NFL_LINK = "http://www.nfl.com";
    private static final String path = "liveupdate/game-center";
    private static final String FORMAT = ".json";
    private int year;
    @Autowired
    private ScheduleRepository sr;
    @Autowired
    private GameDataRepository gdr;
    @Autowired
    private Client client;
    private List<NflSchedule> schedules;
    private ApplicationContext ctx;
    private boolean isTaskComplete;

    public void setSearchYear(int year) {
        this.year = year;
        isTaskComplete = false;
        Map<String, Integer> params = new HashMap();
        params.put("year", 2013);
        schedules = sr.findByNamedQuery(NflSchedule.class, "Year", params);
        ctx = new AnnotationConfigApplicationContext(AggregatorConfig.class);
    }

    @Override
    public boolean taskComplete() {
        return isTaskComplete;
    }

    /**
     * Runs the implemented task
     */
    @Override
    public void run() {
        if (Objects.nonNull(year) && Objects.nonNull(schedules)) {

            if (schedules.isEmpty()) {
                isTaskComplete = true;
                return;
            }
            NflSchedule sched = schedules.remove(0);

            WebTarget target = client.target(BASE_NFL_LINK).path(path)
                    .path(sched.getGameDate()).path(sched.getGameDate() + "_gtd" + FORMAT);

            Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

            if (response.getStatus() == 200) {
                String nflData = response.readEntity(String.class);

                GameData gd = ctx.getBean(GameData.class);
                GameDataPK gdPK = ctx.getBean(GameDataPK.class);

                String identifier = nflData.substring(2, 12);
                gdPK.setGameIdentifier(identifier);
                gdPK.setYear(year);

                gd.setGameDataPK(gdPK);

                gd.setGame(nflData);
                gdr.create(gd);

                GameData gdTest = gdr.find(gdPK);
            }
        }
    }
}
