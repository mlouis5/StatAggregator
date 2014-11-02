/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.workers;

import com.fantasy.stataggregator.annotations.TaskRunner;
import com.fantasy.stataggregator.entities.Player;
import com.fantasy.stataggregator.entities.Team;
import com.fantasy.stataggregator.entities.dao.impl.PlayerRepository;
import com.fantasy.stataggregator.entities.dao.impl.TeamRepository;
import static com.fantasy.stataggregator.workers.FieldSetter.SDF;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mac
 */
@TaskRunner
public class TeamRetrieverTask extends DataRetriever {

    //private static final Pattern BAD_DOB = Pattern.compile("0{4}-0{2}-0{2}");
    private static final String TEAM_ARRAY_KEY = "NFLTeams";
    private static final String[] MEMBER_KEYS = {"code", "fullName",
        "shortName"};

    @Autowired
    private TeamRepository tr;

    public TeamRetrieverTask() {
        super("nfl-teams");
    }

    @Override
    public void run() throws IllegalAccessException, ParseException,
            NoSuchFieldException, JSONException {
        JSONObject teams = makeRequest();

        if (Objects.nonNull(teams)) {
            JSONArray jsonArr = teams.getJSONArray(TEAM_ARRAY_KEY);            
            List<Team> tms = getEntities(jsonArr);

            if (!tms.isEmpty()) {
                tr = ctx.getBean(TeamRepository.class);
                tms.stream().forEach((team) -> {
                    tr.create(team);
                });
            }
        }
        isTaskComplete = true;
    }

    private List<Team> getEntities(JSONArray jsonArr) throws
            IllegalArgumentException, IllegalAccessException,
            ParseException, JSONException, NoSuchFieldException {
        List<Team> entityContainer = new ArrayList(32);
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject singleGame = jsonArr.getJSONObject(i);

            Team team = ctx.getBean(Team.class);

            for (String key : MEMBER_KEYS) {
                Field field;
                try {
                    field = team.getClass()
                            .getDeclaredField(key.toLowerCase());
                } catch (NoSuchFieldException ex) {
                    field = team.getClass().getDeclaredField(key);
                }
                fieldSetter(team, field, singleGame.getString(key));
            }
            entityContainer.add(team);
        }
        return entityContainer;
    }

    public static void main(String[] args) throws IllegalAccessException, 
            ParseException, NoSuchFieldException, JSONException {
        TeamRetrieverTask prt = new TeamRetrieverTask();
        prt.run();
    }

}