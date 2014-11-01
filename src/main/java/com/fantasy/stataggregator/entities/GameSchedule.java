/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MacDerson
 */
@Entity
@Table(name = "game_schedule", catalog = "fantasy", schema = "drafttool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GameSchedule.findAll", query = "SELECT g FROM GameSchedule g"),
    @NamedQuery(name = "GameSchedule.findByGameid", query = "SELECT g FROM GameSchedule g WHERE g.gameid = :gameid"),
    @NamedQuery(name = "GameSchedule.findByAwayteam", query = "SELECT g FROM GameSchedule g WHERE g.awayteam = :awayteam"),
    @NamedQuery(name = "GameSchedule.findByGamedate", query = "SELECT g FROM GameSchedule g WHERE g.gamedate = :gamedate"),
    @NamedQuery(name = "GameSchedule.findByGametimeet", query = "SELECT g FROM GameSchedule g WHERE g.gametimeet = :gametimeet"),
    @NamedQuery(name = "GameSchedule.findByGameweek", query = "SELECT g FROM GameSchedule g WHERE g.gameweek = :gameweek"),
    @NamedQuery(name = "GameSchedule.findByHometeam", query = "SELECT g FROM GameSchedule g WHERE g.hometeam = :hometeam"),
    @NamedQuery(name = "GameSchedule.findByTvstation", query = "SELECT g FROM GameSchedule g WHERE g.tvstation = :tvstation"),
    @NamedQuery(name = "GameSchedule.findByWinner", query = "SELECT g FROM GameSchedule g WHERE g.winner = :winner")})
public class GameSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "gameid", nullable = false)
    private Integer gameid;
    @Column(name = "awayteam", length = 3)
    private String awayteam;
    @Basic(optional = false)
    @Column(name = "gamedate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date gamedate;
    @Column(name = "gametimeet", length = 8)
    private String gametimeet;
    @Column(name = "gameweek")
    private Integer gameweek;
    @Column(name = "hometeam", length = 3)
    private String hometeam;
    @Column(name = "tvstation", length = 11)
    private String tvstation;
    @Column(name = "winner", length = 3)
    private String winner;

    public GameSchedule() {
    }

    public GameSchedule(Integer gameid) {
        this.gameid = gameid;
    }

    public GameSchedule(Integer gameid, Date gamedate) {
        this.gameid = gameid;
        this.gamedate = gamedate;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public String getAwayteam() {
        return awayteam;
    }

    public void setAwayteam(String awayteam) {
        this.awayteam = awayteam;
    }

    public Date getGamedate() {
        return gamedate;
    }

    public void setGamedate(Date gamedate) {
        this.gamedate = gamedate;
    }

    public String getGametimeet() {
        return gametimeet;
    }

    public void setGametimeet(String gametimeet) {
        this.gametimeet = gametimeet;
    }

    public Integer getGameweek() {
        return gameweek;
    }

    public void setGameweek(Integer gameweek) {
        this.gameweek = gameweek;
    }

    public String getHometeam() {
        return hometeam;
    }

    public void setHometeam(String hometeam) {
        this.hometeam = hometeam;
    }

    public String getTvstation() {
        return tvstation;
    }

    public void setTvstation(String tvstation) {
        this.tvstation = tvstation;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameid != null ? gameid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameSchedule)) {
            return false;
        }
        GameSchedule other = (GameSchedule) object;
        if ((this.gameid == null && other.gameid != null) || (this.gameid != null && !this.gameid.equals(other.gameid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fantasy.stataggregator.entities.GameSchedule[ gameid=" + gameid + " ]";
    }
    
}
