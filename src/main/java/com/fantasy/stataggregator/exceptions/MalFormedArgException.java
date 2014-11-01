/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.exceptions;

/**
 *
 * @author Mac
 */
public class MalFormedArgException extends Exception {
    
    private String msg;
    
    public MalFormedArgException(){
        this.msg = "Command line arguments are malformed";
    }
    
    public MalFormedArgException(String msg){
        this.msg = msg;
    }
    
    @Override
    public String toString(){
        return msg;
    }
}
