/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.abstracts;

import com.fantasy.stataggregator.Stats;
import com.fantasy.stataggregator.annotations.Accumulates;
import com.fantasy.stataggregator.annotations.Maximum;

/**
 *
 * @author MacDerson
 * @param <T>
 */
public abstract class PostionalStat<T extends Stats> extends Stat<T> {

    @Accumulates
    private int yds;
    @Accumulates
    private int tds;
    @Maximum
    private int lng;
    @Maximum
    private int lngTd;
    @Accumulates
    private int twopta;
    @Accumulates
    private int twoptm;
    
    public PostionalStat(String id, String name, String team, String position) {
        super(id, name, team, position);
    }

    public final int getYds() {
        return yds;
    }

    public final void setYds(int yds) {
        this.yds = yds;
    }

    public final int getTds() {
        return tds;
    }

    public final void setTds(int tds) {
        this.tds = tds;
    }

    public final int getLng() {
        return lng;
    }

    public final void setLng(int lng) {
        this.lng = lng;
    }

    public final int getLngTd() {
        return lngTd;
    }

    public final void setLngTd(int lngTd) {
        this.lngTd = lngTd;
    }

    public final int getTwopta() {
        return twopta;
    }

    public final void setTwopta(int twopta) {
        this.twopta = twopta;
    }

    public final int getTwoptm() {
        return twoptm;
    }

    public final void setTwoptm(int twoptm) {
        this.twoptm = twoptm;
    }
    
    @Override
    public abstract T getStat();
    
}
