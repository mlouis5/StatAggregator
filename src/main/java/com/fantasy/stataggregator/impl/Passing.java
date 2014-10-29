/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.impl;

import com.fantasy.stataggregator.Absorbable;
import com.fantasy.stataggregator.Absorber;
import com.fantasy.stataggregator.abstracts.Stat;
import com.fantasy.stataggregator.annotations.Accumulates;
import com.fantasy.stataggregator.annotations.Maximum;
import com.google.common.base.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MacDerson
 */
public class Passing extends Stat<Passing> implements Absorber<Passing>, Absorbable<Passing> {

    @Accumulates
    private int att;
    @Accumulates
    private int cmp;
    @Accumulates
    private int yds;
    @Accumulates
    private int tds;
    @Accumulates
    private int ints;
    @Accumulates
    private int twopta;
    @Accumulates
    private int twoptm;

    public Passing(String id, String name, String team, String position) {
        super(id, name, team, position);
    }

    public int getAtt() {
        return att;
    }

    public void setAtt(int att) {
        this.att = att;
    }

    public int getCmp() {
        return cmp;
    }

    public void setCmp(int cmp) {
        this.cmp = cmp;
    }

    public int getYds() {
        return yds;
    }

    public void setYds(int yds) {
        this.yds = yds;
    }

    public int getTds() {
        return tds;
    }

    public void setTds(int tds) {
        this.tds = tds;
    }

    public int getInts() {
        return ints;
    }

    public void setInts(int ints) {
        this.ints = ints;
    }

    public int getTwopta() {
        return twopta;
    }

    public void setTwopta(int twopta) {
        this.twopta = twopta;
    }

    public int getTwoptm() {
        return twoptm;
    }

    public void setTwoptm(int twoptm) {
        this.twoptm = twoptm;
    }

    @Override
    public Passing getStat() {
        return this;
    }

    @Override
    public void absorb(Absorbable<Passing> absorbable, Class<Passing> clazz) {
        Preconditions.checkNotNull(clazz, "Class<Passing> may not be null");
        Preconditions.checkNotNull(absorbable, "absorbable may not be null");

        if (!Objects.equals(hashCode(), absorbable.hashCode())) {
            return;
        }
        Passing passing = (Passing) clazz.cast(absorbable);
        this.setAtt(this.getAtt() + passing.getAtt());
        this.setCmp(this.getCmp() + passing.getCmp());
        this.setInts(this.getInts() + passing.getInts());
        this.setTds(this.getTds() + passing.getTds());
        this.setTwopta(this.getTwopta() + passing.getTwopta());
        this.setTwoptm(this.getTwoptm() + passing.getTwoptm());
    }

    

}
