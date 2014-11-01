/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MacDerson
 */
@Entity
@Table(name = "player", catalog = "fantasy", schema = "drafttool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findByPlayerId", query = "SELECT p FROM Player p WHERE p.playerId = :playerId"),
    @NamedQuery(name = "Player.findByActive", query = "SELECT p FROM Player p WHERE p.active = :active"),
    @NamedQuery(name = "Player.findByCollege", query = "SELECT p FROM Player p WHERE p.college = :college"),
    @NamedQuery(name = "Player.findByDisplayname", query = "SELECT p FROM Player p WHERE p.displayname = :displayname"),
    @NamedQuery(name = "Player.findByDob", query = "SELECT p FROM Player p WHERE p.dob = :dob"),
    @NamedQuery(name = "Player.findByFname", query = "SELECT p FROM Player p WHERE p.fname = :fname"),
    @NamedQuery(name = "Player.findByHeight", query = "SELECT p FROM Player p WHERE p.height = :height"),
    @NamedQuery(name = "Player.findByJersey", query = "SELECT p FROM Player p WHERE p.jersey = :jersey"),
    @NamedQuery(name = "Player.findByLname", query = "SELECT p FROM Player p WHERE p.lname = :lname"),
    @NamedQuery(name = "Player.findByPosition", query = "SELECT p FROM Player p WHERE p.position = :position"),
    @NamedQuery(name = "Player.findByWeight", query = "SELECT p FROM Player p WHERE p.weight = :weight")})
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "player_id", nullable = false)
    private Integer playerId;
    @Column(name = "active")
    private Integer active;
    @Column(name = "college", length = 128)
    private String college;
    @Column(name = "displayname", length = 257)
    private String displayname;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Column(name = "fname", length = 128)
    private String fname;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "height", precision = 17, scale = 17)
    private Double height;
    @Column(name = "jersey")
    private Integer jersey;
    @Column(name = "lname", length = 128)
    private String lname;
    @Column(name = "position", length = 3)
    private String position;
    @Column(name = "weight")
    private Integer weight;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "player")
    private Statistic statistic;
    @JoinColumn(name = "team", referencedColumnName = "code")
    @ManyToOne
    private Team team;

    public Player() {
    }

    public Player(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getJersey() {
        return jersey;
    }

    public void setJersey(Integer jersey) {
        this.jersey = jersey;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playerId != null ? playerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.playerId == null && other.playerId != null) || (this.playerId != null && !this.playerId.equals(other.playerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fantasy.stataggregator.entities.Player[ playerId=" + playerId + " ]";
    }
    
}
