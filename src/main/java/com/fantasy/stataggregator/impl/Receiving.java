/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.impl;

import com.fantasy.stataggregator.Absorbable;
import com.fantasy.stataggregator.Absorber;
import com.fantasy.stataggregator.abstracts.PostionalStat;
import com.fantasy.stataggregator.annotations.Accumulates;
import com.google.common.base.Preconditions;
import java.util.Objects;

/**
 *
 * @author MacDerson
 */
public class Receiving extends PostionalStat<Receiving> implements Absorber<Receiving>, Absorbable<Receiving> {

    @Accumulates
    private int rec;
    
    public Receiving(String id, String name, String team, String position) {
        super(id, name, team, position);
    }
    
    public int getRec() {
        return rec;
    }

    public void setRec(int rec) {
        this.rec = rec;
    }

    @Override
    public Receiving getStat() {
        return this;
    }

    @Override
    public void absorb(Absorbable<Receiving> absorbable, Class<Receiving> clazz) {
        Preconditions.checkNotNull(clazz, "Class<Passing> may not be null");
        Preconditions.checkNotNull(absorbable, "absorbable may not be null");

        if (!Objects.equals(hashCode(), absorbable.hashCode())) {
            return;
        }
        Receiving receiving = (Receiving) clazz.cast(absorbable);
        this.setRec(this.getRec() + receiving.getRec());
        this.setLng(this.getLng() + receiving.getLng());
        this.setLngTd(this.getLngTd() > receiving.getLngTd() ? this.getLngTd() : receiving.getLngTd());
        this.setTds(this.getTds() + receiving.getTds());
        this.setTwopta(this.getTwopta() + receiving.getTwopta());
        this.setTwoptm(this.getTwoptm() + receiving.getTwoptm());
        this.setYds(this.getYds() + receiving.getYds());
    }
    
}
