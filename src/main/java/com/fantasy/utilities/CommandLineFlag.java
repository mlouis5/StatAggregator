/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.utilities;

import java.util.regex.Pattern;

/**
 *
 * @author MacDerson
 * @param <T>
 * @param <C>
 */
public interface CommandLineFlag<T extends Enum, C extends CommandLineContainer> {
    
    Pattern value();
    
    Pattern captureFormat();
    
    default boolean matches(Pattern p, String input){
        return p.matcher(input).matches();
    }
    
    default boolean isFlag(String input){
        return input.matches("^((-)|(--).*)$");
    }
    
    boolean capturesData(); 
    
    T[] overrides();
    
    Class<C> forContainer();
}
