/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.workers;

import com.fantasy.AggregatorConfig;
import com.fantasy.stataggregator.Task;
import com.fantasy.stataggregator.YearlyTask;
import com.fantasy.stataggregator.annotations.TaskRunner;
import com.fantasy.stataggregator.entities.GameData;
import com.fantasy.stataggregator.entities.GameDataPK;
import com.fantasy.stataggregator.entities.GameSchedule;
import com.fantasy.stataggregator.entities.GameSchedule_;
import com.fantasy.stataggregator.entities.dao.impl.GameDataRepository;
import com.fantasy.stataggregator.entities.dao.impl.GameScheduleRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
@TaskRunner
public class GameDataRetrieverTask implements YearlyTask {

    private static final int FIRST_INDEX = 0;
    private static final String pathSeparator = "/";
    private static final String BASE_NFL_LINK = "http://www.nfl.com";
    private static final String path = "liveupdate/game-center";
    private static final String FORMAT = ".json";
    @Autowired
    private SimpleDateFormat sdf;
    private int year;
    @Autowired
    private GameScheduleRepository gsr;
    @Autowired
    private GameDataRepository gdr;
    @Autowired
    private Client client;
    private List<GameSchedule> schedules;
    private ApplicationContext ctx;
    private boolean isTaskComplete;
    int count = 1;

    /**
     * Sets the statistical year to be requested.
     *
     * @param year
     * @throws java.text.ParseException
     */
    @Override
    public void setYear(int year) throws ParseException {
        this.year = year;
        isTaskComplete = false;
        sdf.applyLocalizedPattern("yyyyMMdd");
        
        Date min = sdf.parse(year + "0101");
        Date max = sdf.parse(year + "1231");
        
        CriteriaBuilder cb = gsr.getCriteriaBuilder();
            CriteriaQuery<GameSchedule> cq = gsr.getCriteriaQuery();
            Root<GameSchedule> gameSchedule = gsr.getRoot();
            cq.select(gameSchedule).where(cb.between(gameSchedule.get(GameSchedule_.gamedate),
                    min, max)).orderBy(cb.asc(gameSchedule.get(GameSchedule_.gameid)));
                    
        schedules = gsr.getCriteriaList(cq);
        System.out.println(schedules.size());
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
     * Retrieves a JSON String representing a single NFL game played, on a given
     * day. Then save this data to the database, uniquely<br>
     * identified by the schedule. each schedule occurs on a given date<br>
     * in the format yyyyMMdd\d{2}, where \d{2} is a two digit number<br>
     * identifying 1 of n games played that day (range 00 - 15 or
     * fewer).<br><br>
     *
     * run retrieves one game at a time, it is up the the controlling class, to
     * recursively call this method.
     * @throws java.text.ParseException
     * @throws java.lang.InterruptedException
     */
    @Override
    public void run() throws ParseException, InterruptedException {
        if (Objects.nonNull(year) && Objects.nonNull(schedules)) {
            if (schedules.isEmpty()) {
                isTaskComplete = true;
                return;
            }
            
            //get the earliest scheduled game
            GameSchedule sched = schedules.remove(FIRST_INDEX);

            //if this NflSchedule is in the past, proceed
            if (Objects.nonNull(sched) && hasBeenPlayed(sched)) {
                Response response = requestData(sched);

                //if request was successful, proceed with saving data.
                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    String nflData = response.readEntity(String.class);

                    persistGameData(sched.getGameid(), nflData);
                    System.out.println(count);
                }
                count++;
            }else if(Objects.nonNull(sched) && !hasBeenPlayed(sched)){
                isTaskComplete = true;
            }
        }
        Thread.sleep(2000L);
    }

    /**
     * Make a request to nfl.com for the data identified by the NflSchedule
     *
     * @param sched
     * @return
     */
    private Response requestData(GameSchedule sched) {
        WebTarget target = client.target(BASE_NFL_LINK).path(path)
                .path(sched.getGameid().toString()).path(sched.getGameid() + "_gtd" + FORMAT);

        return target.request(MediaType.APPLICATION_JSON_TYPE).get();
    }

    /**
     * Determines if a game has already been played, No current game data<br>
     * will be pulled, since the statistics aren't final.
     *
     * @param sched
     * @return
     */
    private boolean hasBeenPlayed(GameSchedule sched) throws ParseException {
            Date gameDate = sched.getGamedate();
            if (Objects.nonNull(gameDate)) {
                LocalDate dateOnly = LocalDateTime.ofInstant(
                        gameDate.toInstant(), ZoneId.systemDefault()).toLocalDate();

                return dateOnly.isBefore(LocalDate.now());
            }        
        return false;
    }

    /**
     * Saves the game data as a single long text string in the database<br>
     * to be processed later.
     *
     * @param identifier
     * @param nflData
     */
    private void persistGameData(int identifier, String nflData) {
        GameData gd = ctx.getBean(GameData.class);
        GameDataPK gdPK = ctx.getBean(GameDataPK.class);

        gdPK.setGameIdentifier(String.valueOf(identifier));
        gdPK.setYear(year);

        gd.setGameDataPK(gdPK);

        gd.setGame(nflData);
        gdr.create(gd);

        GameData gdTest = gdr.find(gdPK);
    }

}
