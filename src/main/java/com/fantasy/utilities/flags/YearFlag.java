/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.utilities.flags;

import com.fantasy.utilities.CommandLineFlag;
import com.fantasy.utilities.containers.YearContainer;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 *
 * @author Mac
 */
public enum YearFlag implements CommandLineFlag<YearFlag, YearContainer>{

    YEAR("^(-y|-yr|--year)$", true, "^(2[0-9][1-9]\\d)$"), 
    ALL("^(-\\*|--all|-a)$", false, null, YearFlag.YEAR),
    PACKAGE("", true, "^([a-z]+)$"),
    NAME("^(-n|-nm|--name)$", true, "^([a-z]+)(\\.)([A-Z][a-z]+)+(\\.)(java)$"),
    ;
    
    private final Pattern value;
    private final boolean capturesData;
    private final Pattern pattern;
    private final YearFlag[] overrides;
    
    YearFlag(String value, boolean capturesData, String pattern, YearFlag... overrides){
        this.value = Pattern.compile(value);
        this.capturesData = capturesData;
        this.pattern = Pattern.compile(Optional.ofNullable(pattern).orElse("^(.*)$")); 
        this.overrides = Optional.ofNullable(overrides).orElse(new YearFlag[0]);
    }

    @Override
    public Pattern value() {
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
    public YearFlag[] overrides() {
        return overrides;
    }

    @Override
    public Class<YearContainer> forContainer() {
        return YearContainer.class;
    }
    
}