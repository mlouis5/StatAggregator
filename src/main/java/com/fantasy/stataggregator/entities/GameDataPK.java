/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Mac
 */
@Embeddable
public class GameDataPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "game_identifier", nullable = false, length = 10)
    private String gameIdentifier;
    @Basic(optional = false)
    @Column(name = "year", nullable = false)
    private int year;

    public GameDataPK() {
    }

    public GameDataPK(String gameIdentifier, int year) {
        this.gameIdentifier = gameIdentifier;
        this.year = year;
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }

    public void setGameIdentifier(String gameIdentifier) {
        this.gameIdentifier = gameIdentifier;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameIdentifier != null ? gameIdentifier.hashCode() : 0);
        hash += (int) year;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameDataPK)) {
            return false;
        }
        GameDataPK other = (GameDataPK) object;
        if ((this.gameIdentifier == null && other.gameIdentifier != null) || (this.gameIdentifier != null && !this.gameIdentifier.equals(other.gameIdentifier))) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fantasy.stataggregator.entities.GameDataPK[ gameIdentifier=" + gameIdentifier + ", year=" + year + " ]";
    }
    
}
