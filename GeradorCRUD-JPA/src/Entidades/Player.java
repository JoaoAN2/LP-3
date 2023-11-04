/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author joaoan2
 */
@Entity
@Table(name = "player")
@NamedQueries({
    @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "Player.findByIdPlayer", query = "SELECT p FROM Player p WHERE p.idPlayer = :idPlayer"),
    @NamedQuery(name = "Player.findByNamePlayer", query = "SELECT p FROM Player p WHERE p.namePlayer = :namePlayer"),
    @NamedQuery(name = "Player.findByPointsPlayer", query = "SELECT p FROM Player p WHERE p.pointsPlayer = :pointsPlayer"),
    @NamedQuery(name = "Player.findByBirthdayPlayer", query = "SELECT p FROM Player p WHERE p.birthdayPlayer = :birthdayPlayer")})
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_player")
    private Integer idPlayer;
    @Basic(optional = false)
    @Column(name = "name_player")
    private String namePlayer;
    @Basic(optional = false)
    @Column(name = "points_player")
    private int pointsPlayer;
    @Basic(optional = false)
    @Column(name = "birthday_player")
    @Temporal(TemporalType.DATE)
    private Date birthdayPlayer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List<Tournaments> tournamentsList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "player")
    private Referee referee;
    @JoinColumn(name = "federation_sigla_player", referencedColumnName = "sigla_federation")
    @ManyToOne(optional = false)
    private Federation federationSiglaPlayer;
    @JoinColumn(name = "gender_sigla_player", referencedColumnName = "sigla_gender")
    @ManyToOne(optional = false)
    private Gender genderSiglaPlayer;
    @JoinColumn(name = "title_sigla_player", referencedColumnName = "sigla_title")
    @ManyToOne(optional = false)
    private Title titleSiglaPlayer;

    public Player() {
    }

    public Player(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Player(Integer idPlayer, String namePlayer, int pointsPlayer, Date birthdayPlayer) {
        this.idPlayer = idPlayer;
        this.namePlayer = namePlayer;
        this.pointsPlayer = pointsPlayer;
        this.birthdayPlayer = birthdayPlayer;
    }

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getNamePlayer() {
        return namePlayer;
    }

    public void setNamePlayer(String namePlayer) {
        this.namePlayer = namePlayer;
    }

    public int getPointsPlayer() {
        return pointsPlayer;
    }

    public void setPointsPlayer(int pointsPlayer) {
        this.pointsPlayer = pointsPlayer;
    }

    public Date getBirthdayPlayer() {
        return birthdayPlayer;
    }

    public void setBirthdayPlayer(Date birthdayPlayer) {
        this.birthdayPlayer = birthdayPlayer;
    }

    public List<Tournaments> getTournamentsList() {
        return tournamentsList;
    }

    public void setTournamentsList(List<Tournaments> tournamentsList) {
        this.tournamentsList = tournamentsList;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Federation getFederationSiglaPlayer() {
        return federationSiglaPlayer;
    }

    public void setFederationSiglaPlayer(Federation federationSiglaPlayer) {
        this.federationSiglaPlayer = federationSiglaPlayer;
    }

    public Gender getGenderSiglaPlayer() {
        return genderSiglaPlayer;
    }

    public void setGenderSiglaPlayer(Gender genderSiglaPlayer) {
        this.genderSiglaPlayer = genderSiglaPlayer;
    }

    public Title getTitleSiglaPlayer() {
        return titleSiglaPlayer;
    }

    public void setTitleSiglaPlayer(Title titleSiglaPlayer) {
        this.titleSiglaPlayer = titleSiglaPlayer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlayer != null ? idPlayer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.idPlayer == null && other.idPlayer != null) || (this.idPlayer != null && !this.idPlayer.equals(other.idPlayer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Player[ idPlayer=" + idPlayer + " ]";
    }
    
}
