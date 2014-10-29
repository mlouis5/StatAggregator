/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities;

import java.io.Serializable;
import java.sql.Blob;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mac
 */
@Entity
@Table(name = "game_data", catalog = "fantasy", schema = "drafttool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GameData.findAll", query = "SELECT g FROM GameData g"),
    @NamedQuery(name = "GameData.findByGame", query = "SELECT g FROM GameData g WHERE g.game = :game"),
    @NamedQuery(name = "GameData.findByYear", query = "SELECT g FROM GameData g WHERE g.year = :year")})
public class GameData implements Serializable {
    @Column(name = "game")
    private String game;
    @Id
    @Basic(optional = false)
    @Column(name = "game_identifier", nullable = false, length = 10)
    private String gameIdentifier;
    private static final long serialVersionUID = 1L;
    @Column(name = "year")
    private Integer year;

    public GameData() {
    }

    public GameData(String gameIdentifier) {
        this.gameIdentifier = gameIdentifier;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }

    public void setGameIdentifier(String gameIdentifier) {
        this.gameIdentifier = gameIdentifier;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameIdentifier != null ? gameIdentifier.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameData)) {
            return false;
        }
        GameData other = (GameData) object;
        if ((this.gameIdentifier == null && other.gameIdentifier != null) || (this.gameIdentifier != null && !this.gameIdentifier.equals(other.gameIdentifier))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fantasy.stataggregator.entities.GameData[ gameIdentifier=" + gameIdentifier + " ]";
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
    
}
