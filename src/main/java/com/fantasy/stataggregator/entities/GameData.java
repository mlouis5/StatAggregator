/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    @NamedQuery(name = "GameData.findByGameIdentifier", query = "SELECT g FROM GameData g WHERE g.gameDataPK.gameIdentifier = :gameIdentifier"),
    @NamedQuery(name = "GameData.findByGame", query = "SELECT g FROM GameData g WHERE g.game = :game"),
    @NamedQuery(name = "GameData.findByYear", query = "SELECT g FROM GameData g WHERE g.gameDataPK.year = :year")})
public class GameData implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GameDataPK gameDataPK;
    @Lob
    @Column(name = "game", length = 2147483647)
    private String game;

    public GameData() {
    }

    public GameData(GameDataPK gameDataPK) {
        this.gameDataPK = gameDataPK;
    }

    public GameData(String gameIdentifier, int year) {
        this.gameDataPK = new GameDataPK(gameIdentifier, year);
    }

    public GameDataPK getGameDataPK() {
        return gameDataPK;
    }

    public void setGameDataPK(GameDataPK gameDataPK) {
        this.gameDataPK = gameDataPK;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameDataPK != null ? gameDataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GameData)) {
            return false;
        }
        GameData other = (GameData) object;
        
        return Objects.nonNull(this.gameDataPK) 
                && Objects.nonNull(other.gameDataPK) 
                && Objects.equals(this.gameDataPK, other.gameDataPK);
    }

    @Override
    public String toString() {
        return "com.fantasy.stataggregator.entities.GameData[ gameDataPK=" + gameDataPK + " ]";
    }
}
