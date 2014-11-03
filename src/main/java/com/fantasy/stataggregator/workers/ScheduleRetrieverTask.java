/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.workers;

import com.fantasy.stataggregator.annotations.TaskRunner;
import com.fantasy.stataggregator.entities.GameSchedule;
import com.fantasy.stataggregator.entities.dao.impl.GameScheduleRepository;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mac
 */
@TaskRunner
public class ScheduleRetrieverTask extends DataRetriever {

    private static final String SCHEDULE_ARRAY_KEY = "Schedule";
    private static final String[] SCHEDULE_KEYS = {"gameId", "gameWeek", "gameDate", "awayTeam", "homeTeam", "gameTimeET", "tvStation", "winner"};
    
    @Autowired
    private GameScheduleRepository gsr;

    public ScheduleRetrieverTask() {
        super("schedule");
    }

    @Override
    public void run() {
        JSONObject schedule = makeRequest();

        if (Objects.nonNull(schedule)) {
            try {
                JSONArray jsonArr = schedule.getJSONArray(SCHEDULE_ARRAY_KEY);
                List<GameSchedule> gs = getSchedules(jsonArr);
                
                if(!gs.isEmpty()){
                    setTrueGameId(gs);
                    
                    gsr = ctx.getBean(GameScheduleRepository.class);
                    gs.stream().forEach((gameSched) -> {
                        gsr.create(gameSched);
                    });
                    
                }
            } catch (JSONException | IllegalArgumentException 
                    | IllegalAccessException | ParseException 
                    | NoSuchFieldException ex) {
                Logger.getLogger(ScheduleRetrieverTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    
//    public static void main(String[] args){
//        ScheduleRetrieverTask srt = new ScheduleRetrieverTask();
//        srt.run();
//    }
    
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
