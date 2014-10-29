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
import com.google.common.base.Preconditions;
import java.util.Objects;

/**
 *
 * @author MacDerson
 */
public class Fumbles extends Stat<Fumbles> implements Absorber<Fumbles>, Absorbable<Fumbles> {

    @Accumulates
    private int tot;
    @Accumulates
    private int rcv;
    @Accumulates
    private int trcv;
    @Accumulates
    private int yds;
    @Accumulates
    private int lost;
    
    public Fumbles(String id, String name, String team, String position) {
        super(id, name, team, position);
    }

    public int getTot() {
        return tot;
    }

    public void setTot(int tot) {
        this.tot = tot;
    }

    public int getRcv() {
        return rcv;
    }

    public void setRcv(int rcv) {
        this.rcv = rcv;
    }

    public int getTrcv() {
        return trcv;
    }

    public void setTrcv(int trcv) {
        this.trcv = trcv;
    }

    public int getYds() {
        return yds;
    }

    public void setYds(int yds) {
        this.yds = yds;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    @Override
    public Fumbles getStat() {
        return this;
    }

    @Override
    public void absorb(Absorbable<Fumbles> absorbable, Class<Fumbles> clazz) {
        Preconditions.checkNotNull(clazz, "Class<Passing> may not be null");
        Preconditions.checkNotNull(absorbable, "absorbable may not be null");

        if (!Objects.equals(hashCode(), absorbable.hashCode())) {
            return;
        }
        Fumbles fumbles = (Fumbles) clazz.cast(absorbable);
        this.setLost(this.getLost() + fumbles.getLost());
        this.setRcv(this.getRcv() + fumbles.getRcv());
        this.setTot(this.getTot() + fumbles.getTot());
        this.setTrcv(this.getTrcv() + fumbles.getTrcv());
        this.setYds(this.getYds() + fumbles.getYds());
    }
    
}
