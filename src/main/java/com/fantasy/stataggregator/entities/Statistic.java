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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MacDerson
 */
@Entity
@Table(name = "statistic", catalog = "fantasy", schema = "drafttool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Statistic.findAll", query = "SELECT s FROM Statistic s"),
    @NamedQuery(name = "Statistic.findByPlayerId", query = "SELECT s FROM Statistic s WHERE s.playerId = :playerId"),
    @NamedQuery(name = "Statistic.findByDefenseAst", query = "SELECT s FROM Statistic s WHERE s.defenseAst = :defenseAst"),
    @NamedQuery(name = "Statistic.findByDefenseFfum", query = "SELECT s FROM Statistic s WHERE s.defenseFfum = :defenseFfum"),
    @NamedQuery(name = "Statistic.findByDefenseInt", query = "SELECT s FROM Statistic s WHERE s.defenseInt = :defenseInt"),
    @NamedQuery(name = "Statistic.findByDefenseSk", query = "SELECT s FROM Statistic s WHERE s.defenseSk = :defenseSk"),
    @NamedQuery(name = "Statistic.findByDefenseTkl", query = "SELECT s FROM Statistic s WHERE s.defenseTkl = :defenseTkl"),
    @NamedQuery(name = "Statistic.findByFumblesLost", query = "SELECT s FROM Statistic s WHERE s.fumblesLost = :fumblesLost"),
    @NamedQuery(name = "Statistic.findByFumblesRcv", query = "SELECT s FROM Statistic s WHERE s.fumblesRcv = :fumblesRcv"),
    @NamedQuery(name = "Statistic.findByFumblesTot", query = "SELECT s FROM Statistic s WHERE s.fumblesTot = :fumblesTot"),
    @NamedQuery(name = "Statistic.findByFumblesTrcv", query = "SELECT s FROM Statistic s WHERE s.fumblesTrcv = :fumblesTrcv"),
    @NamedQuery(name = "Statistic.findByFumblesYds", query = "SELECT s FROM Statistic s WHERE s.fumblesYds = :fumblesYds"),
    @NamedQuery(name = "Statistic.findByKickingFga", query = "SELECT s FROM Statistic s WHERE s.kickingFga = :kickingFga"),
    @NamedQuery(name = "Statistic.findByKickingFgm", query = "SELECT s FROM Statistic s WHERE s.kickingFgm = :kickingFgm"),
    @NamedQuery(name = "Statistic.findByKickingFgyds", query = "SELECT s FROM Statistic s WHERE s.kickingFgyds = :kickingFgyds"),
    @NamedQuery(name = "Statistic.findByKickingTotpfg", query = "SELECT s FROM Statistic s WHERE s.kickingTotpfg = :kickingTotpfg"),
    @NamedQuery(name = "Statistic.findByKickingXpa", query = "SELECT s FROM Statistic s WHERE s.kickingXpa = :kickingXpa"),
    @NamedQuery(name = "Statistic.findByKickingXpb", query = "SELECT s FROM Statistic s WHERE s.kickingXpb = :kickingXpb"),
    @NamedQuery(name = "Statistic.findByKickingXpmade", query = "SELECT s FROM Statistic s WHERE s.kickingXpmade = :kickingXpmade"),
    @NamedQuery(name = "Statistic.findByKickingXpmissed", query = "SELECT s FROM Statistic s WHERE s.kickingXpmissed = :kickingXpmissed"),
    @NamedQuery(name = "Statistic.findByKickingXptot", query = "SELECT s FROM Statistic s WHERE s.kickingXptot = :kickingXptot"),
    @NamedQuery(name = "Statistic.findByKickretAvg", query = "SELECT s FROM Statistic s WHERE s.kickretAvg = :kickretAvg"),
    @NamedQuery(name = "Statistic.findByKickretLng", query = "SELECT s FROM Statistic s WHERE s.kickretLng = :kickretLng"),
    @NamedQuery(name = "Statistic.findByKickretLngtd", query = "SELECT s FROM Statistic s WHERE s.kickretLngtd = :kickretLngtd"),
    @NamedQuery(name = "Statistic.findByKickretRet", query = "SELECT s FROM Statistic s WHERE s.kickretRet = :kickretRet"),
    @NamedQuery(name = "Statistic.findByKickretTds", query = "SELECT s FROM Statistic s WHERE s.kickretTds = :kickretTds"),
    @NamedQuery(name = "Statistic.findByPassingAtt", query = "SELECT s FROM Statistic s WHERE s.passingAtt = :passingAtt"),
    @NamedQuery(name = "Statistic.findByPassingCmp", query = "SELECT s FROM Statistic s WHERE s.passingCmp = :passingCmp"),
    @NamedQuery(name = "Statistic.findByPassingInts", query = "SELECT s FROM Statistic s WHERE s.passingInts = :passingInts"),
    @NamedQuery(name = "Statistic.findByPassingTds", query = "SELECT s FROM Statistic s WHERE s.passingTds = :passingTds"),
    @NamedQuery(name = "Statistic.findByPassingTwopta", query = "SELECT s FROM Statistic s WHERE s.passingTwopta = :passingTwopta"),
    @NamedQuery(name = "Statistic.findByPassingTwoptm", query = "SELECT s FROM Statistic s WHERE s.passingTwoptm = :passingTwoptm"),
    @NamedQuery(name = "Statistic.findByPassingYds", query = "SELECT s FROM Statistic s WHERE s.passingYds = :passingYds"),
    @NamedQuery(name = "Statistic.findByPuntingAvg", query = "SELECT s FROM Statistic s WHERE s.puntingAvg = :puntingAvg"),
    @NamedQuery(name = "Statistic.findByPuntingI20", query = "SELECT s FROM Statistic s WHERE s.puntingI20 = :puntingI20"),
    @NamedQuery(name = "Statistic.findByPuntingLng", query = "SELECT s FROM Statistic s WHERE s.puntingLng = :puntingLng"),
    @NamedQuery(name = "Statistic.findByPuntingPts", query = "SELECT s FROM Statistic s WHERE s.puntingPts = :puntingPts"),
    @NamedQuery(name = "Statistic.findByPuntingYds", query = "SELECT s FROM Statistic s WHERE s.puntingYds = :puntingYds"),
    @NamedQuery(name = "Statistic.findByPuntretAvg", query = "SELECT s FROM Statistic s WHERE s.puntretAvg = :puntretAvg"),
    @NamedQuery(name = "Statistic.findByPuntretLng", query = "SELECT s FROM Statistic s WHERE s.puntretLng = :puntretLng"),
    @NamedQuery(name = "Statistic.findByPuntretLngtd", query = "SELECT s FROM Statistic s WHERE s.puntretLngtd = :puntretLngtd"),
    @NamedQuery(name = "Statistic.findByPuntretRet", query = "SELECT s FROM Statistic s WHERE s.puntretRet = :puntretRet"),
    @NamedQuery(name = "Statistic.findByPuntretTds", query = "SELECT s FROM Statistic s WHERE s.puntretTds = :puntretTds"),
    @NamedQuery(name = "Statistic.findByReceivingLng", query = "SELECT s FROM Statistic s WHERE s.receivingLng = :receivingLng"),
    @NamedQuery(name = "Statistic.findByReceivingLngtd", query = "SELECT s FROM Statistic s WHERE s.receivingLngtd = :receivingLngtd"),
    @NamedQuery(name = "Statistic.findByReceivingRec", query = "SELECT s FROM Statistic s WHERE s.receivingRec = :receivingRec"),
    @NamedQuery(name = "Statistic.findByReceivingTds", query = "SELECT s FROM Statistic s WHERE s.receivingTds = :receivingTds"),
    @NamedQuery(name = "Statistic.findByReceivingTwopta", query = "SELECT s FROM Statistic s WHERE s.receivingTwopta = :receivingTwopta"),
    @NamedQuery(name = "Statistic.findByReceivingTwoptm", query = "SELECT s FROM Statistic s WHERE s.receivingTwoptm = :receivingTwoptm"),
    @NamedQuery(name = "Statistic.findByReceivingYds", query = "SELECT s FROM Statistic s WHERE s.receivingYds = :receivingYds"),
    @NamedQuery(name = "Statistic.findByRushingAtt", query = "SELECT s FROM Statistic s WHERE s.rushingAtt = :rushingAtt"),
    @NamedQuery(name = "Statistic.findByRushingLng", query = "SELECT s FROM Statistic s WHERE s.rushingLng = :rushingLng"),
    @NamedQuery(name = "Statistic.findByRushingLngtd", query = "SELECT s FROM Statistic s WHERE s.rushingLngtd = :rushingLngtd"),
    @NamedQuery(name = "Statistic.findByRushingTds", query = "SELECT s FROM Statistic s WHERE s.rushingTds = :rushingTds"),
    @NamedQuery(name = "Statistic.findByRushingTwopta", query = "SELECT s FROM Statistic s WHERE s.rushingTwopta = :rushingTwopta"),
    @NamedQuery(name = "Statistic.findByRushingTwoptm", query = "SELECT s FROM Statistic s WHERE s.rushingTwoptm = :rushingTwoptm"),
    @NamedQuery(name = "Statistic.findByRushingYds", query = "SELECT s FROM Statistic s WHERE s.rushingYds = :rushingYds"),
    @NamedQuery(name = "Statistic.findByStatYear", query = "SELECT s FROM Statistic s WHERE s.statYear = :statYear")})
public class Statistic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "player_id", nullable = false)
    private Integer playerId;
    @Column(name = "defense_ast")
    private Integer defenseAst;
    @Column(name = "defense_ffum")
    private Integer defenseFfum;
    @Column(name = "defense_int")
    private Integer defenseInt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "defense_sk", precision = 17, scale = 17)
    private Double defenseSk;
    @Column(name = "defense_tkl")
    private Integer defenseTkl;
    @Column(name = "fumbles_lost")
    private Integer fumblesLost;
    @Column(name = "fumbles_rcv")
    private Integer fumblesRcv;
    @Column(name = "fumbles_tot")
    private Integer fumblesTot;
    @Column(name = "fumbles_trcv")
    private Integer fumblesTrcv;
    @Column(name = "fumbles_yds")
    private Integer fumblesYds;
    @Column(name = "kicking_fga")
    private Integer kickingFga;
    @Column(name = "kicking_fgm")
    private Integer kickingFgm;
    @Column(name = "kicking_fgyds")
    private Integer kickingFgyds;
    @Column(name = "kicking_totpfg")
    private Integer kickingTotpfg;
    @Column(name = "kicking_xpa")
    private Integer kickingXpa;
    @Column(name = "kicking_xpb")
    private Integer kickingXpb;
    @Column(name = "kicking_xpmade")
    private Integer kickingXpmade;
    @Column(name = "kicking_xpmissed")
    private Integer kickingXpmissed;
    @Column(name = "kicking_xptot")
    private Integer kickingXptot;
    @Column(name = "kickret_avg", precision = 17, scale = 17)
    private Double kickretAvg;
    @Column(name = "kickret_lng")
    private Integer kickretLng;
    @Column(name = "kickret_lngtd")
    private Integer kickretLngtd;
    @Column(name = "kickret_ret")
    private Integer kickretRet;
    @Column(name = "kickret_tds")
    private Integer kickretTds;
    @Column(name = "passing_att")
    private Integer passingAtt;
    @Column(name = "passing_cmp")
    private Integer passingCmp;
    @Column(name = "passing_ints")
    private Integer passingInts;
    @Column(name = "passing_tds")
    private Integer passingTds;
    @Column(name = "passing_twopta")
    private Integer passingTwopta;
    @Column(name = "passing_twoptm")
    private Integer passingTwoptm;
    @Column(name = "passing_yds")
    private Integer passingYds;
    @Column(name = "punting_avg", precision = 17, scale = 17)
    private Double puntingAvg;
    @Column(name = "punting_i20")
    private Integer puntingI20;
    @Column(name = "punting_lng")
    private Integer puntingLng;
    @Column(name = "punting_pts")
    private Integer puntingPts;
    @Column(name = "punting_yds")
    private Integer puntingYds;
    @Column(name = "puntret_avg", precision = 17, scale = 17)
    private Double puntretAvg;
    @Column(name = "puntret_lng")
    private Integer puntretLng;
    @Column(name = "puntret_lngtd")
    private Integer puntretLngtd;
    @Column(name = "puntret_ret")
    private Integer puntretRet;
    @Column(name = "puntret_tds")
    private Integer puntretTds;
    @Column(name = "receiving_lng")
    private Integer receivingLng;
    @Column(name = "receiving_lngtd")
    private Integer receivingLngtd;
    @Column(name = "receiving_rec")
    private Integer receivingRec;
    @Column(name = "receiving_tds")
    private Integer receivingTds;
    @Column(name = "receiving_twopta")
    private Integer receivingTwopta;
    @Column(name = "receiving_twoptm")
    private Integer receivingTwoptm;
    @Column(name = "receiving_yds")
    private Integer receivingYds;
    @Column(name = "rushing_att")
    private Integer rushingAtt;
    @Column(name = "rushing_lng")
    private Integer rushingLng;
    @Column(name = "rushing_lngtd")
    private Integer rushingLngtd;
    @Column(name = "rushing_tds")
    private Integer rushingTds;
    @Column(name = "rushing_twopta")
    private Integer rushingTwopta;
    @Column(name = "rushing_twoptm")
    private Integer rushingTwoptm;
    @Column(name = "rushing_yds")
    private Integer rushingYds;
    @Column(name = "stat_year")
    private Integer statYear;
    @XmlTransient
    @JoinColumn(name = "player_id", referencedColumnName = "player_id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Player player;

    public Statistic() {
    }

    public Statistic(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getDefenseAst() {
        return defenseAst;
    }

    public void setDefenseAst(Integer defenseAst) {
        this.defenseAst = defenseAst;
    }

    public Integer getDefenseFfum() {
        return defenseFfum;
    }

    public void setDefenseFfum(Integer defenseFfum) {
        this.defenseFfum = defenseFfum;
    }

    public Integer getDefenseInt() {
        return defenseInt;
    }

    public void setDefenseInt(Integer defenseInt) {
        this.defenseInt = defenseInt;
    }

    public Double getDefenseSk() {
        return defenseSk;
    }

    public void setDefenseSk(Double defenseSk) {
        this.defenseSk = defenseSk;
    }

    public Integer getDefenseTkl() {
        return defenseTkl;
    }

    public void setDefenseTkl(Integer defenseTkl) {
        this.defenseTkl = defenseTkl;
    }

    public Integer getFumblesLost() {
        return fumblesLost;
    }

    public void setFumblesLost(Integer fumblesLost) {
        this.fumblesLost = fumblesLost;
    }

    public Integer getFumblesRcv() {
        return fumblesRcv;
    }

    public void setFumblesRcv(Integer fumblesRcv) {
        this.fumblesRcv = fumblesRcv;
    }

    public Integer getFumblesTot() {
        return fumblesTot;
    }

    public void setFumblesTot(Integer fumblesTot) {
        this.fumblesTot = fumblesTot;
    }

    public Integer getFumblesTrcv() {
        return fumblesTrcv;
    }

    public void setFumblesTrcv(Integer fumblesTrcv) {
        this.fumblesTrcv = fumblesTrcv;
    }

    public Integer getFumblesYds() {
        return fumblesYds;
    }

    public void setFumblesYds(Integer fumblesYds) {
        this.fumblesYds = fumblesYds;
    }

    public Integer getKickingFga() {
        return kickingFga;
    }

    public void setKickingFga(Integer kickingFga) {
        this.kickingFga = kickingFga;
    }

    public Integer getKickingFgm() {
        return kickingFgm;
    }

    public void setKickingFgm(Integer kickingFgm) {
        this.kickingFgm = kickingFgm;
    }

    public Integer getKickingFgyds() {
        return kickingFgyds;
    }

    public void setKickingFgyds(Integer kickingFgyds) {
        this.kickingFgyds = kickingFgyds;
    }

    public Integer getKickingTotpfg() {
        return kickingTotpfg;
    }

    public void setKickingTotpfg(Integer kickingTotpfg) {
        this.kickingTotpfg = kickingTotpfg;
    }

    public Integer getKickingXpa() {
        return kickingXpa;
    }

    public void setKickingXpa(Integer kickingXpa) {
        this.kickingXpa = kickingXpa;
    }

    public Integer getKickingXpb() {
        return kickingXpb;
    }

    public void setKickingXpb(Integer kickingXpb) {
        this.kickingXpb = kickingXpb;
    }

    public Integer getKickingXpmade() {
        return kickingXpmade;
    }

    public void setKickingXpmade(Integer kickingXpmade) {
        this.kickingXpmade = kickingXpmade;
    }

    public Integer getKickingXpmissed() {
        return kickingXpmissed;
    }

    public void setKickingXpmissed(Integer kickingXpmissed) {
        this.kickingXpmissed = kickingXpmissed;
    }

    public Integer getKickingXptot() {
        return kickingXptot;
    }

    public void setKickingXptot(Integer kickingXptot) {
        this.kickingXptot = kickingXptot;
    }

    public Double getKickretAvg() {
        return kickretAvg;
    }

    public void setKickretAvg(Double kickretAvg) {
        this.kickretAvg = kickretAvg;
    }

    public Integer getKickretLng() {
        return kickretLng;
    }

    public void setKickretLng(Integer kickretLng) {
        this.kickretLng = kickretLng;
    }

    public Integer getKickretLngtd() {
        return kickretLngtd;
    }

    public void setKickretLngtd(Integer kickretLngtd) {
        this.kickretLngtd = kickretLngtd;
    }

    public Integer getKickretRet() {
        return kickretRet;
    }

    public void setKickretRet(Integer kickretRet) {
        this.kickretRet = kickretRet;
    }

    public Integer getKickretTds() {
        return kickretTds;
    }

    public void setKickretTds(Integer kickretTds) {
        this.kickretTds = kickretTds;
    }

    public Integer getPassingAtt() {
        return passingAtt;
    }

    public void setPassingAtt(Integer passingAtt) {
        this.passingAtt = passingAtt;
    }

    public Integer getPassingCmp() {
        return passingCmp;
    }

    public void setPassingCmp(Integer passingCmp) {
        this.passingCmp = passingCmp;
    }

    public Integer getPassingInts() {
        return passingInts;
    }

    public void setPassingInts(Integer passingInts) {
        this.passingInts = passingInts;
    }

    public Integer getPassingTds() {
        return passingTds;
    }

    public void setPassingTds(Integer passingTds) {
        this.passingTds = passingTds;
    }

    public Integer getPassingTwopta() {
        return passingTwopta;
    }

    public void setPassingTwopta(Integer passingTwopta) {
        this.passingTwopta = passingTwopta;
    }

    public Integer getPassingTwoptm() {
        return passingTwoptm;
    }

    public void setPassingTwoptm(Integer passingTwoptm) {
        this.passingTwoptm = passingTwoptm;
    }

    public Integer getPassingYds() {
        return passingYds;
    }

    public void setPassingYds(Integer passingYds) {
        this.passingYds = passingYds;
    }

    public Double getPuntingAvg() {
        return puntingAvg;
    }

    public void setPuntingAvg(Double puntingAvg) {
        this.puntingAvg = puntingAvg;
    }

    public Integer getPuntingI20() {
        return puntingI20;
    }

    public void setPuntingI20(Integer puntingI20) {
        this.puntingI20 = puntingI20;
    }

    public Integer getPuntingLng() {
        return puntingLng;
    }

    public void setPuntingLng(Integer puntingLng) {
        this.puntingLng = puntingLng;
    }

    public Integer getPuntingPts() {
        return puntingPts;
    }

    public void setPuntingPts(Integer puntingPts) {
        this.puntingPts = puntingPts;
    }

    public Integer getPuntingYds() {
        return puntingYds;
    }

    public void setPuntingYds(Integer puntingYds) {
        this.puntingYds = puntingYds;
    }

    public Double getPuntretAvg() {
        return puntretAvg;
    }

    public void setPuntretAvg(Double puntretAvg) {
        this.puntretAvg = puntretAvg;
    }

    public Integer getPuntretLng() {
        return puntretLng;
    }

    public void setPuntretLng(Integer puntretLng) {
        this.puntretLng = puntretLng;
    }

    public Integer getPuntretLngtd() {
        return puntretLngtd;
    }

    public void setPuntretLngtd(Integer puntretLngtd) {
        this.puntretLngtd = puntretLngtd;
    }

    public Integer getPuntretRet() {
        return puntretRet;
    }

    public void setPuntretRet(Integer puntretRet) {
        this.puntretRet = puntretRet;
    }

    public Integer getPuntretTds() {
        return puntretTds;
    }

    public void setPuntretTds(Integer puntretTds) {
        this.puntretTds = puntretTds;
    }

    public Integer getReceivingLng() {
        return receivingLng;
    }

    public void setReceivingLng(Integer receivingLng) {
        this.receivingLng = receivingLng;
    }

    public Integer getReceivingLngtd() {
        return receivingLngtd;
    }

    public void setReceivingLngtd(Integer receivingLngtd) {
        this.receivingLngtd = receivingLngtd;
    }

    public Integer getReceivingRec() {
        return receivingRec;
    }

    public void setReceivingRec(Integer receivingRec) {
        this.receivingRec = receivingRec;
    }

    public Integer getReceivingTds() {
        return receivingTds;
    }

    public void setReceivingTds(Integer receivingTds) {
        this.receivingTds = receivingTds;
    }

    public Integer getReceivingTwopta() {
        return receivingTwopta;
    }

    public void setReceivingTwopta(Integer receivingTwopta) {
        this.receivingTwopta = receivingTwopta;
    }

    public Integer getReceivingTwoptm() {
        return receivingTwoptm;
    }

    public void setReceivingTwoptm(Integer receivingTwoptm) {
        this.receivingTwoptm = receivingTwoptm;
    }

    public Integer getReceivingYds() {
        return receivingYds;
    }

    public void setReceivingYds(Integer receivingYds) {
        this.receivingYds = receivingYds;
    }

    public Integer getRushingAtt() {
        return rushingAtt;
    }

    public void setRushingAtt(Integer rushingAtt) {
        this.rushingAtt = rushingAtt;
    }

    public Integer getRushingLng() {
        return rushingLng;
    }

    public void setRushingLng(Integer rushingLng) {
        this.rushingLng = rushingLng;
    }

    public Integer getRushingLngtd() {
        return rushingLngtd;
    }

    public void setRushingLngtd(Integer rushingLngtd) {
        this.rushingLngtd = rushingLngtd;
    }

    public Integer getRushingTds() {
        return rushingTds;
    }

    public void setRushingTds(Integer rushingTds) {
        this.rushingTds = rushingTds;
    }

    public Integer getRushingTwopta() {
        return rushingTwopta;
    }

    public void setRushingTwopta(Integer rushingTwopta) {
        this.rushingTwopta = rushingTwopta;
    }

    public Integer getRushingTwoptm() {
        return rushingTwoptm;
    }

    public void setRushingTwoptm(Integer rushingTwoptm) {
        this.rushingTwoptm = rushingTwoptm;
    }

    public Integer getRushingYds() {
        return rushingYds;
    }

    public void setRushingYds(Integer rushingYds) {
        this.rushingYds = rushingYds;
    }

    public Integer getStatYear() {
        return statYear;
    }

    public void setStatYear(Integer statYear) {
        this.statYear = statYear;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        if (!(object instanceof Statistic)) {
            return false;
        }
        Statistic other = (Statistic) object;
        if ((this.playerId == null && other.playerId != null) || (this.playerId != null && !this.playerId.equals(other.playerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fantasy.stataggregator.entities.Statistic[ playerId=" + playerId + " ]";
    }
    
}
