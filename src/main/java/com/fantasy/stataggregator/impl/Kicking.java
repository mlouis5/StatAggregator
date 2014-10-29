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
public class Kicking extends Stat<Kicking> implements Absorber<Kicking>, Absorbable<Kicking>{

    @Accumulates
    private int fgm;
    @Accumulates
    private int fga;
    @Accumulates
    private int fgYds;
    @Accumulates
    private int totfg;
    @Accumulates
    private int xpmade;
    @Accumulates
    private int xpmissed;
    @Accumulates
    private int xpa;
    @Accumulates
    private int xpb; //extra points blocked
    @Accumulates
    private int xpTot; //total extra points
    
    public Kicking(String id, String name, String team, String position) {
        super(id, name, team, position);
    }

    @Override
    public Kicking getStat() {
        return this;
    }

    public int getTotfg() {
        return totfg;
    }

    public void setTotfg(int totfg) {
        this.totfg = totfg;
    }

    public int getXpmade() {
        return xpmade;
    }

    public void setXpmade(int xpmade) {
        this.xpmade = xpmade;
    }

    public int getXpmissed() {
        return xpmissed;
    }

    public void setXpmissed(int xpmissed) {
        this.xpmissed = xpmissed;
    }

    public int getXpa() {
        return xpa;
    }

    public void setXpa(int xpa) {
        this.xpa = xpa;
    }

    public int getXpb() {
        return xpb;
    }

    public void setXpb(int xpb) {
        this.xpb = xpb;
    }

    public int getXpTot() {
        return xpTot;
    }

    public void setXpTot(int xpTot) {
        this.xpTot = xpTot;
    }

    public int getFgm() {
        return fgm;
    }

    public void setFgm(int fgm) {
        this.fgm = fgm;
    }

    public int getFga() {
        return fga;
    }

    public void setFga(int fga) {
        this.fga = fga;
    }

    public int getFgYds() {
        return fgYds;
    }

    public void setFgYds(int fgYds) {
        this.fgYds = fgYds;
    }

    @Override
    public void absorb(Absorbable<Kicking> absorbable, Class<Kicking> clazz) {
        Preconditions.checkNotNull(clazz, "Class<Passing> may not be null");
        Preconditions.checkNotNull(absorbable, "absorbable may not be null");

        if (!Objects.equals(hashCode(), absorbable.hashCode())) {
            return;
        }
        Kicking kicking = (Kicking) clazz.cast(absorbable);
        this.setFgYds(this.getFgYds() + kicking.getFgYds());
        this.setFga(this.getFga() + kicking.getFga());
        this.setFgm(this.getFgm() + kicking.getFgm());
        this.setTotfg(this.getTotfg() + kicking.getTotfg());
        this.setXpTot(this.getXpTot() + kicking.getXpTot());
        this.setXpa(this.getXpa() + kicking.getXpa());
        this.setXpb(this.getXpb() + kicking.getXpb());
        this.setXpmade(this.getXpmade() + kicking.getXpmade());
        this.setXpmissed(this.getXpmissed() + kicking.getXpmissed());
    }
    
}
