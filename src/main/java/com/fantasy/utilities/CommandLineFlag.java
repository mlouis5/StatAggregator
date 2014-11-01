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
 */
public interface CommandLineFlag<T extends Enum> {
    
    String value();
    
    Pattern captureFormat();
    
    boolean capturesData(); 
    
    T[] overrides();
}
