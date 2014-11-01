/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.utilities;

/**
 *
 * @author MacDerson
 */
public interface CommandLineParser {
    
    CommandLineContainer parseFor(CommandLineFlag<? extends Enum> flag);
}
