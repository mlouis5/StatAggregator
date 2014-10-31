/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.workers;

import com.fantasy.AggregatorConfig;
import com.fantasy.stataggregator.Task;
import com.fantasy.stataggregator.entities.GameSchedule;
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

    public ScheduleRetrieverTask() {
        ctx = new AnnotationConfigApplicationContext(AggregatorConfig.class);
        SDF = ctx.getBean(SimpleDateFormat.class);
        SDF.applyLocalizedPattern("yyyy-MM-dd");
    }

    @Override
    public boolean taskComplete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        JSONObject schedule = requestSchedule();

        if (Objects.nonNull(schedule)) {
            try {
                JSONArray jsonArr = schedule.getJSONArray(SCHEDULE_ARRAY_KEY);
                List<GameSchedule> gs = getSchedules(jsonArr);
                
                setTrueGameId(gs);
                
                //persist GameSchedule
            } catch (JSONException ex) {
                Logger.getLogger(ScheduleRetrieverTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<GameSchedule> getSchedules(JSONArray jsonArr) {
        List<GameSchedule> games = new ArrayList(256);
        for (int i = 0; i < jsonArr.length(); i++) {
            try {
                //{"gameId":"1","gameWeek":"1","gameDate":"2014-09-04","awayTeam":"GB","homeTeam":"SEA","gameTimeET":"8:30 PM","tvStation":"NBC","winner":"SEA"}
                JSONObject singleGame = jsonArr.getJSONObject(i);

                GameSchedule gameSchedule = ctx.getBean(GameSchedule.class);
                
                for (String key : SCHEDULE_KEYS) {
                    Field field = gameSchedule.getClass().getDeclaredField(key.toLowerCase());
                    field.setAccessible(true);
                    fieldSetter(gameSchedule, field, singleGame.getString(key));
                    field.setAccessible(false);
                }
                System.out.println(gameSchedule);
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
    
    public static void main(String[] args){
        ScheduleRetrieverTask srt = new ScheduleRetrieverTask();
        srt.run();
    }
    
    public void fieldSetter(Object instance, Field field, String value) throws IllegalArgumentException, IllegalAccessException, ParseException{
        Class<?> type = field.getType();
        System.out.println("type: " + type);
        if(type.equals(Integer.class)){
            field.set(instance, Integer.parseInt(value));
        }else if(type.equals(Double.class)){
            field.set(instance, Double.parseDouble(value));
        }else if(type.equals(Date.class)){
            field.set(instance, SDF.parse(value));
        }else if(type.equals(String.class)){
            field.set(instance, value);
        }
        System.out.println(field.get(instance));
    }
    
    private void setTrueGameId(List<GameSchedule> games){
        int count = 0;
        String latestDate = null;
        for(GameSchedule game : games){
            String formattedDate = SDF.format(game.getGamedate()).replaceAll("-", "");
            System.out.println("latestDate: " + latestDate + "\t" + formattedDate);
            if(latestDate == null){
                latestDate = formattedDate;
            }else if(!Objects.equals(latestDate, formattedDate)){
                latestDate = formattedDate;
                count = 0;
            }
            String id = count < 10 ? (latestDate + "0" + count) : (latestDate + count);
            System.out.println(id);
            game.setGameid(id);            
            count++;
        }
    }

}
