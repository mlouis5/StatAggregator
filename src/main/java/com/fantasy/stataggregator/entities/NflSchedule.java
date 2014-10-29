/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MacDerson
 */
@Entity
@Table(name = "nfl_schedule", catalog = "fantasy", schema = "drafttool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NflSchedule.findAll", query = "SELECT n FROM NflSchedule n"),
    @NamedQuery(name = "NflSchedule.findByGameDate", query = "SELECT n FROM NflSchedule n WHERE n.gameDate = :gameDate"),
    @NamedQuery(name = "NflSchedule.findByYear", query = "SELECT n FROM NflSchedule n WHERE n.year = :year")})
public class NflSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "game_date", nullable = false, length = 10)
    private String gameDate;
    @Column(name = "year")
    private Integer year;

    public NflSchedule() {
    }

    public NflSchedule(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameDate != null ? gameDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NflSchedule)) {
            return false;
        }
        NflSchedule other = (NflSchedule) object;
        if ((this.gameDate == null && other.gameDate != null) || (this.gameDate != null && !this.gameDate.equals(other.gameDate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fantasy.stataggregator.entities.NflSchedule[ gameDate=" + gameDate + " ]";
    }
    
}
