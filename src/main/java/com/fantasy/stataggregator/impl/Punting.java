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
import com.fantasy.stataggregator.annotations.Average;
import com.fantasy.stataggregator.annotations.Maximum;
import com.google.common.base.Preconditions;
import java.util.Objects;

/**
 *
 * @author MacDerson
 */
public class Punting extends Stat<Punting> implements Absorber<Punting>, Absorbable<Punting>{

    @Accumulates
    private int pts;    //punts
    @Accumulates
    private int yds;
    @Average(avgOfFieldName="yds")
    private double avg;
    @Accumulates
    private int i20;    //inside 20
    @Maximum
    private int lng;
    
    public Punting(String id, String name, String team, String position) {
        super(id, name, team, position);
    }

    @Override
    public Punting getStat() {
        return this;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getYds() {
        return yds;
    }

    public void setYds(int yds) {
        this.yds = yds;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public int getI20() {
        return i20;
    }

    public void setI20(int i20) {
        this.i20 = i20;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    @Override
    public void absorb(Absorbable<Punting> absorbable, Class<Punting> clazz) {
        Preconditions.checkNotNull(clazz, "Class<Passing> may not be null");
        Preconditions.checkNotNull(absorbable, "absorbable may not be null");

        if (!Objects.equals(hashCode(), absorbable.hashCode())) {
            return;
        }
        Punting punting = (Punting) clazz.cast(absorbable);
        this.setAvg((this.getAvg() + punting.getAvg()) / 2);
        this.setI20(this.getI20() + punting.getI20());
        this.setLng(punting.getLng() > this.getLng() ? punting.getLng() : this.getLng());
        this.setPts(this.getPts() + punting.getPts());
        this.setYds(this.getYds() + punting.getYds());
    }
    
}
