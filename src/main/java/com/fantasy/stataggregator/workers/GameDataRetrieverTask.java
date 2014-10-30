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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * GameDataRetrieverTask retrieves every json file from nfl.com's game center
 * for a given year<br>
 * then persist that data as a LOB in a postgresql database.
 *
 * @version %I%, %G%
 * @author MacDerson
 */
public class GameDataRetrieverTask implements Task {

    private static final int FIRST_INDEX = 0;
    private static final String pathSeparator = "/";
    private static final String BASE_NFL_LINK = "http://www.nfl.com";
    private static final String path = "liveupdate/game-center";
    private static final String FORMAT = ".json";
    @Autowired
    private SimpleDateFormat sdf;
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

    /**
     * Sets the statistical year to be requested.
     * @param year 
     */
    public void setSearchYear(int year) {
        this.year = year;
        isTaskComplete = false;
        sdf.applyLocalizedPattern("yyyyMMdd");
        Map<String, Integer> params = new HashMap();
        params.put("year", 2013);
        schedules = sr.findByNamedQuery(NflSchedule.class, "Year", params);
        ctx = new AnnotationConfigApplicationContext(AggregatorConfig.class);
    }

    /**
     * Returns true if the task has completed, In the case of
     * GameDataRetrieverTask<br>
     * taskComplete will only return true if the every game has been retrieved
     *
     * @return
     */
    @Override
    public boolean taskComplete() {
        return isTaskComplete;
    }

    /**
     * Retrieves a JSON String representing a single NFL game played,
     * on a given day. Then save this data to the database, uniquely<br>
     * identified by the schedule. each schedule occurs on a given date<br>
     * in the format yyyyMMdd\d{2}, where \d{2} is a two digit number<br>
     * identifying 1 of n games played that day (range 00 - 15 or fewer).<br><br>
     * 
     * run retrieves one game at a time, it is up the the controlling class,
     * to recursively call this method.
     */
    @Override
    public void run() {
        if (Objects.nonNull(year) && Objects.nonNull(schedules)) {
            if (schedules.isEmpty()) {
                isTaskComplete = true;
                return;
            }

            NflSchedule sched = schedules.remove(FIRST_INDEX);

            //if this NflSchedule is in the past, proceed
            if (hasBeenPlayed(sched)) {
                Response response = requestData(sched);

                //if request was successful, proceed with saving data.
                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    String nflData = response.readEntity(String.class);

                    String identifier = findIdentifier(sched.getGameDate(), nflData);

                    //If identifier was found, persist data.
                    if (Objects.nonNull(identifier) && !identifier.isEmpty()) {
                        persistGameData(identifier, nflData);
                    }
                }
            }

        }
    }

    /**
     * Retrieves this Games identifier which is a date plus game order<br>
     * Format: yyyyMMdd\d{2}
     * @param gameSchedule
     * @param nflData
     * @return 
     */
    private String findIdentifier(String gameSchedule, String nflData) {
        String identifier = null;
        try {
            JSONObject jsonOb = new JSONObject(nflData);
            Iterator iter = jsonOb.keys();

            while (iter.hasNext()) {
                Object key = iter.next();
                if (key.toString().equals(gameSchedule)) {
                    identifier = key.toString();
                    break;
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(GameDataRetrieverTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        return identifier;
    }

    /**
     * Make a request to nfl.com for the data identified by the NflSchedule
     * @param sched
     * @return 
     */
    private Response requestData(NflSchedule sched) {
        WebTarget target = client.target(BASE_NFL_LINK).path(path)
                .path(sched.getGameDate()).path(sched.getGameDate() + "_gtd" + FORMAT);

        return target.request(MediaType.APPLICATION_JSON_TYPE).get();
    }

    /**
     * Determines if a game has already been played, No current game data<br>
     * will be pulled, since the statistics aren't final.
     * @param sched
     * @return 
     */
    private boolean hasBeenPlayed(NflSchedule sched) {
        try {
            String schedDate = sched.getGameDate().substring(0, 8);
            Date date = sdf.parse(schedDate);

            LocalDate dateOnly = LocalDateTime.ofInstant(
                    date.toInstant(), ZoneId.systemDefault()).toLocalDate();

            return dateOnly.isBefore(LocalDate.now());
        } catch (ParseException ex) {
            Logger.getLogger(GameDataRetrieverTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Saves the game data as a single long text string in the database<br>
     * to be processed later.
     * @param identifier
     * @param nflData 
     */
    private void persistGameData(String identifier, String nflData) {
        GameData gd = ctx.getBean(GameData.class);
        GameDataPK gdPK = ctx.getBean(GameDataPK.class);

        gdPK.setGameIdentifier(identifier);
        gdPK.setYear(year);

        gd.setGameDataPK(gdPK);

        gd.setGame(nflData);
        gdr.create(gd);

        GameData gdTest = gdr.find(gdPK);
    }

}
