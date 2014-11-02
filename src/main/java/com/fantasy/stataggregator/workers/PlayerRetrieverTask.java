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
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Mac
 */
@TaskRunner
public class PlayerRetrieverTask extends DataRetriever {

    private static final Pattern BAD_DOB = Pattern.compile("0{4}-0{2}-0{2}");
    private static final String PLAYER_ARRAY_KEY = "Players";
    private static final String[] MEMBER_KEYS = {"playerId", "active",
        "jersey", "lname", "fname", "displayName", "team", "position",
        "height", "weight", "dob", "college"};

    @Autowired
    private PlayerRepository pr;
    
    public PlayerRetrieverTask() {
        super("players");
    }

    @Override
    public void run() throws IllegalAccessException, ParseException,
            NoSuchFieldException, JSONException {
        JSONObject players = makeRequest();

        if (Objects.nonNull(players)) {
            JSONArray jsonArr = players.getJSONArray(PLAYER_ARRAY_KEY);            
            List<Player> plyrs = getEntities(jsonArr);

            if (!plyrs.isEmpty()) {
                pr = ctx.getBean(PlayerRepository.class);
                plyrs.stream().forEach((player) -> {
                    pr.create(player);
                });
            }
        }
        isTaskComplete = true;
    }

    private List<Player> getEntities(JSONArray jsonArr) throws
            IllegalArgumentException, IllegalAccessException,
            ParseException, JSONException, NoSuchFieldException {
        List<Player> entityContainer = new ArrayList(2500);
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject singleGame = jsonArr.getJSONObject(i);

            Player player = ctx.getBean(Player.class);
            TeamRepository tr = ctx.getBean(TeamRepository.class);
            List<Team> teams = tr.findAll();

            for (String key : MEMBER_KEYS) {
                Field field;
                try {
                    field = player.getClass()
                            .getDeclaredField(key.toLowerCase());
                } catch (NoSuchFieldException ex) {
                    field = player.getClass().getDeclaredField(key);
                }
                String value = singleGame.getString(key);
                if(key.equalsIgnoreCase("height")){
                    value = value.replace("-", ".");
                }else if(BAD_DOB.matcher(value).matches()){
                    value = null;
                }else if(key.equalsIgnoreCase("team")){
                    for(Team team : teams){
                        if(team.getCode().equalsIgnoreCase(value)){
                            player.setTeam(team);
                            break;
                        }
                    }
                }
                fieldSetter(player, field, value);
            }
            entityContainer.add(player);
        }
        return entityContainer;
    }

    public static void main(String[] args) throws IllegalAccessException, 
            ParseException, NoSuchFieldException, JSONException {
        PlayerRetrieverTask prt = new PlayerRetrieverTask();
        prt.run();
    }

}
