/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author joaoan2
 */
@Entity
@Table(name = "referee")
@NamedQueries({
    @NamedQuery(name = "Referee.findAll", query = "SELECT r FROM Referee r"),
    @NamedQuery(name = "Referee.findByPlayerIdPlayer", query = "SELECT r FROM Referee r WHERE r.playerIdPlayer = :playerIdPlayer")})
public class Referee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "player_id_player")
    private Integer playerIdPlayer;
    @JoinTable(name = "arbitration", joinColumns = {
        @JoinColumn(name = "referee_player_id_player", referencedColumnName = "player_id_player")}, inverseJoinColumns = {
        @JoinColumn(name = "tournament_id_tournament", referencedColumnName = "id_tournament")})
    @ManyToMany
    private List<Tournament> tournamentList;
    @JoinColumn(name = "cat_referee_id_referee", referencedColumnName = "sigla_cat_referee")
    @ManyToOne(optional = false)
    private CatReferee catRefereeIdReferee;
    @JoinColumn(name = "player_id_player", referencedColumnName = "id_player", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Player player;

    public Referee() {
    }

    public Referee(Integer playerIdPlayer) {
        this.playerIdPlayer = playerIdPlayer;
    }

    public Integer getPlayerIdPlayer() {
        return playerIdPlayer;
    }

    public void setPlayerIdPlayer(Integer playerIdPlayer) {
        this.playerIdPlayer = playerIdPlayer;
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    public CatReferee getCatRefereeIdReferee() {
        return catRefereeIdReferee;
    }

    public void setCatRefereeIdReferee(CatReferee catRefereeIdReferee) {
        this.catRefereeIdReferee = catRefereeIdReferee;
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
        hash += (playerIdPlayer != null ? playerIdPlayer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Referee)) {
            return false;
        }
        Referee other = (Referee) object;
        if ((this.playerIdPlayer == null && other.playerIdPlayer != null) || (this.playerIdPlayer != null && !this.playerIdPlayer.equals(other.playerIdPlayer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Referee[ playerIdPlayer=" + playerIdPlayer + " ]";
    }
    
}
