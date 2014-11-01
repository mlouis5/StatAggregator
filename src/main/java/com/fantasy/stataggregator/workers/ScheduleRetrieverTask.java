/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.workers;

import com.fantasy.AggregatorConfig;
import com.fantasy.stataggregator.Task;
import com.fantasy.stataggregator.entities.GameSchedule;
import com.fantasy.stataggregator.entities.dao.impl.GameScheduleRepository;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Mac
 */
public class ScheduleRetrieverTask implements Task {

    private static final String RESOURCE_LOCATOR = "http://www.fantasyfootballnerd.com/service/schedule/";
    private static final String FORMAT = "json";
    private static final String KEY = "guxtr5ihdj7t";

    private static final String SCHEDULE_ARRAY_KEY = "Schedule";
    private static final String[] SCHEDULE_KEYS = {"gameId", "gameWeek", "gameDate", "awayTeam", "homeTeam", "gameTimeET", "tvStation", "winner"};
    private static SimpleDateFormat SDF;
    private ApplicationContext ctx;
    private boolean isTaskComplete;
    @Autowired
    private GameScheduleRepository gsr;

    public ScheduleRetrieverTask() {
        ctx = new AnnotationConfigApplicationContext(AggregatorConfig.class);
        SDF = ctx.getBean(SimpleDateFormat.class);
        SDF.applyLocalizedPattern("yyyy-MM-dd");
        isTaskComplete = false;
    }

    @Override
    public boolean taskComplete() {
        return isTaskComplete;
    }

    @Override
    public void run() {
        JSONObject schedule = requestSchedule();

        if (Objects.nonNull(schedule)) {
            try {
                JSONArray jsonArr = schedule.getJSONArray(SCHEDULE_ARRAY_KEY);
                List<GameSchedule> gs = getSchedules(jsonArr);
                
                if(!gs.isEmpty()){
                    setTrueGameId(gs);
                    
                    gsr = ctx.getBean(GameScheduleRepository.class);
                    for(GameSchedule gameSched : gs){
                        gsr.create(gameSched);
                    }
                    
                }
            } catch (JSONException | IllegalArgumentException 
                    | IllegalAccessException | ParseException 
                    | NoSuchFieldException ex) {
                Logger.getLogger(ScheduleRetrieverTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isTaskComplete = true;
    }

    private List<GameSchedule> getSchedules(JSONArray jsonArr) {
        List<GameSchedule> games = new ArrayList(256);
        for (int i = 0; i < jsonArr.length(); i++) {
            try {
                JSONObject singleGame = jsonArr.getJSONObject(i);

                GameSchedule gameSchedule = ctx.getBean(GameSchedule.class);
                
                for (String key : SCHEDULE_KEYS) {
                    Field field = gameSchedule.getClass().getDeclaredField(key.toLowerCase());
                    fieldSetter(gameSchedule, field, singleGame.getString(key));
                }
                
                games.add(gameSchedule);
            } catch (JSONException | NoSuchFieldException | SecurityException 
                    | IllegalArgumentException | IllegalAccessException 
                    | ParseException ex) {
                Logger.getLogger(ScheduleRetrieverTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return games;
    }

    private JSONObject requestSchedule() {
        Client client = ctx.getBean(Client.class);
        WebTarget target = client.target(RESOURCE_LOCATOR).path(FORMAT)
                .path(KEY);

        Response res = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        if (res.getStatus() == Response.Status.OK.getStatusCode()) {
            String schedule = Optional.ofNullable(res.readEntity(String.class)).orElse("");
            if (!schedule.isEmpty()) {
                JSONObject sched;
                try {
                    sched = new JSONObject(schedule);
                    return sched;
                } catch (JSONException ex) {
                    Logger.getLogger(ScheduleRetrieverTask.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return null;
    }
    
//    public static void main(String[] args){
//        ScheduleRetrieverTask srt = new ScheduleRetrieverTask();
//        srt.run();
//    }
    
    public void fieldSetter(Object instance, Field field, String value) throws IllegalArgumentException, IllegalAccessException, ParseException{
        Class<?> type = field.getType();
        field.setAccessible(true);
        if(type.equals(Integer.class)){
            field.set(instance, Integer.parseInt(value));
        }else if(type.equals(Double.class)){
            field.set(instance, Double.parseDouble(value));
        }else if(type.equals(Date.class)){
            field.set(instance, SDF.parse(value));
        }else if(type.equals(String.class)){
            field.set(instance, value);
        }
        field.setAccessible(false);
    }
    
    private void setTrueGameId(List<GameSchedule> games) throws IllegalArgumentException, IllegalAccessException, ParseException, NoSuchFieldException{
        int count = 0;
        String latestDate = null;
        for(GameSchedule game : games){
            String formattedDate = SDF.format(game.getGamedate()).replaceAll("-", "");
            
            if(latestDate == null){
                latestDate = formattedDate;
            }else if(!Objects.equals(latestDate, formattedDate)){
                latestDate = formattedDate;
                count = 0;
            }
            Field field = game.getClass().getDeclaredField(SCHEDULE_KEYS[0].toLowerCase());
            fieldSetter(game, field, (latestDate + String.format("%02d", count)));
            count++;
        }
    }

}
