/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator;

/**
 *
 * @author MacDerson
 */
public interface Absorber<T> {
    
    void absorb(Absorbable<T> absorbable, Class<T> clazz);
}
