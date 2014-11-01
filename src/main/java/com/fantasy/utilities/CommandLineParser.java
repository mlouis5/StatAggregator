/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.utilities;

/**
 *
 * @author MacDerson
 * @param <T>
 * @param <V>
 */
public interface CommandLineParser<T extends CommandLineFlag, V extends CommandLineContainer> {
    
    V parseFor(Class<T> flag, String[] args) throws Exception;
}
