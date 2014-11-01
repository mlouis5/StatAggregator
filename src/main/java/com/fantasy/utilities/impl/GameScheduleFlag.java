/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.utilities.impl;

import com.fantasy.utilities.CommandLineFlag;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 *
 * @author MacDerson
 */
public enum GameScheduleFlag implements CommandLineFlag<GameScheduleFlag>{

    YEAR("yr", true, "^(2[0-9][1-9]\\d)$"), ALL("all", false, null, GameScheduleFlag.YEAR);
    
    private final String value;
    private final boolean capturesData;
    private final Pattern pattern;
    private final GameScheduleFlag[] overrides;
    
    GameScheduleFlag(String value, boolean capturesData, String pattern, GameScheduleFlag... overrides){
        this.value = value;
        this.capturesData = capturesData;
        this.pattern = Pattern.compile(Optional.ofNullable(pattern).orElse("^(.*)$")); 
        this.overrides = Optional.ofNullable(overrides).orElse(new GameScheduleFlag[0]);
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public Pattern captureFormat() {
        return pattern;
    }

    @Override
    public boolean capturesData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameScheduleFlag[] overrides() {
        return overrides;
    }
    
}
