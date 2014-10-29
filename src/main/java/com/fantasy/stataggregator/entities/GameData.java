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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "GameData.findById", query = "SELECT g FROM GameData g WHERE g.id = :id"),
    @NamedQuery(name = "GameData.findByGame", query = "SELECT g FROM GameData g WHERE g.game = :game"),
    @NamedQuery(name = "GameData.findByYear", query = "SELECT g FROM GameData g WHERE g.year = :year")})
public class GameData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "game", length = 2147483647)
    private String game;
    @Column(name = "year")
    private Integer year;

    public GameData() {
    }

    public GameData(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GameData)) {
            return false;
        }
        GameData other = (GameData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fantasy.stataggregator.entities.GameData[ id=" + id + " ]";
    }
    
}
