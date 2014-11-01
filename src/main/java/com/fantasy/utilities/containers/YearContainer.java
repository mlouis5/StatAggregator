/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.utilities.containers;

import com.fantasy.utilities.CommandLineContainer;
import com.fantasy.utilities.CommandLineFlag;
import com.fantasy.utilities.flags.YearFlag;
import java.util.Objects;

/**
 *
 * @author Mac
 */
public class YearContainer implements CommandLineContainer {

    private int year;
    private boolean isSet;
    private String packageName;
    private String className;

    public int getYear() {
        return year;
    }

    public YearContainer() {
        year = Integer.MIN_VALUE;
        isSet = false;
    }

    @Override
    public void setValueFor(CommandLineFlag flag, String value) {
        if (Objects.equals(YearFlag.YEAR, flag)) {
            if (year < Integer.MAX_VALUE) {
                year = Integer.parseInt(value);
                setTrue();
            }
        } else if (Objects.equals(YearFlag.ALL, flag)) {
            year = Integer.MAX_VALUE;
            setTrue();
        }
    }

    @Override
    public boolean isSet() {
        return isSet;
    }

    private void setTrue() {
        isSet = true;
    }
}
