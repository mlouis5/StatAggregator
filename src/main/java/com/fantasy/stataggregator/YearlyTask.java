/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator;

/**
 *
 * @author Mac
 */
public interface YearlyTask extends Task{
    
    public static final String START_OF_YEAR = "0101";
    public static final String END_OF_YEAR = "1231";
    
    void setYear(int year) throws Exception;
}
