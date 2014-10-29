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
public class Rushing extends PostionalStat<Rushing> implements Absorber<Rushing>, Absorbable<Rushing> {

    @Accumulates
    private int att;
    
    public Rushing(String id, String name, String team, String position) {
        super(id, name, team, position);
    }

    public int getAtt() {
        return att;
    }

    public void setAtt(int att) {
        this.att = att;
    } 
    
    @Override
    public Rushing getStat() {
        return this;
    }

    @Override
    public void absorb(Absorbable<Rushing> absorbable, Class<Rushing> clazz) {
        Preconditions.checkNotNull(clazz, "Class<Passing> may not be null");
        Preconditions.checkNotNull(absorbable, "absorbable may not be null");

        if (!Objects.equals(hashCode(), absorbable.hashCode())) {
            return;
        }
        Rushing rushing = (Rushing) clazz.cast(absorbable);
        this.setAtt(this.getAtt() + rushing.getAtt());
        this.setLng(this.getLng() + rushing.getLng());
        this.setLngTd(this.getLngTd() > rushing.getLngTd() ? this.getLngTd() : rushing.getLngTd());
        this.setTds(this.getTds() + rushing.getTds());
        this.setTwopta(this.getTwopta() + rushing.getTwopta());
        this.setTwoptm(this.getTwoptm() + rushing.getTwoptm());
        this.setYds(this.getYds() + rushing.getYds());
    }
}
