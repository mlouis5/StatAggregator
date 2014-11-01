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
public interface CommandLineContainer<T extends CommandLineFlag, V extends CommandLineContainer> {
    
    boolean isSet();
    void setValueFor(T flag, String value) throws Exception;
}
