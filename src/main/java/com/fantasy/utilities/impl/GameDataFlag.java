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
public enum GameDataFlag implements CommandLineFlag<GameDataFlag>{

    YEAR("yr", true, "^(2[0-9][1-9]\\d)$"), ALL("all", false, null, GameDataFlag.YEAR);
    
    private final String value;
    private final boolean capturesData;
    private final Pattern pattern;
    private final GameDataFlag[] overrides;
    
    GameDataFlag(String value, boolean capturesData, String pattern, GameDataFlag... overrides){
        this.value = value;
        this.capturesData = capturesData;
        this.pattern = Pattern.compile(Optional.ofNullable(pattern).orElse("^(.*)$")); 
        this.overrides = Optional.ofNullable(overrides).orElse(new GameDataFlag[0]);
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
        return capturesData;
    }

    @Override
    public GameDataFlag[] overrides() {
        return overrides;
    }
    
}