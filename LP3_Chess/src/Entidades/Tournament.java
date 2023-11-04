/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Tools.DateTools;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author joaoan2
 */
@Entity
@Table(name = "tournament")
@NamedQueries({
    @NamedQuery(name = "Tournament.findAll", query = "SELECT t FROM Tournament t"),
    @NamedQuery(name = "Tournament.findByIdTournament", query = "SELECT t FROM Tournament t WHERE t.idTournament = :idTournament"),
    @NamedQuery(name = "Tournament.findByStartDateTournament", query = "SELECT t FROM Tournament t WHERE t.startDateTournament = :startDateTournament"),
    @NamedQuery(name = "Tournament.findByEndDateTournament", query = "SELECT t FROM Tournament t WHERE t.endDateTournament = :endDateTournament"),
    @NamedQuery(name = "Tournament.findByRoundsTournament", query = "SELECT t FROM Tournament t WHERE t.roundsTournament = :roundsTournament")})
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tournament")
    private Integer idTournament;
    @Basic(optional = false)
    @Column(name = "start_date_tournament")
    @Temporal(TemporalType.DATE)
    private Date startDateTournament;
    @Basic(optional = false)
    @Column(name = "end_date_tournament")
    @Temporal(TemporalType.DATE)
    private Date endDateTournament;
    @Basic(optional = false)
    @Column(name = "rounds_tournament")
    private int roundsTournament;
    @ManyToMany(mappedBy = "tournamentList")
    private List<Referee> refereeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    private List<Tournaments> tournamentsList;
    @JoinColumn(name = "city_id_tournament", referencedColumnName = "id_city")
    @ManyToOne(optional = false)
    private City cityIdTournament;

    public Tournament() {
    }

    public Tournament(Integer idTournament) {
        this.idTournament = idTournament;
    }

    public Tournament(Integer idTournament, Date startDateTournament, Date endDateTournament, int roundsTournament) {
        this.idTournament = idTournament;
        this.startDateTournament = startDateTournament;
        this.endDateTournament = endDateTournament;
        this.roundsTournament = roundsTournament;
    }

    public Integer getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(Integer idTournament) {
        this.idTournament = idTournament;
    }

    public Date getStartDateTournament() {
        return startDateTournament;
    }

    public void setStartDateTournament(Date startDateTournament) {
        this.startDateTournament = startDateTournament;
    }

    public Date getEndDateTournament() {
        return endDateTournament;
    }

    public void setEndDateTournament(Date endDateTournament) {
        this.endDateTournament = endDateTournament;
    }

    public int getRoundsTournament() {
        return roundsTournament;
    }

    public void setRoundsTournament(int roundsTournament) {
        this.roundsTournament = roundsTournament;
    }

    public List<Referee> getRefereeList() {
        return refereeList;
    }

    public void setRefereeList(List<Referee> refereeList) {
        this.refereeList = refereeList;
    }

    public List<Tournaments> getTournamentsList() {
        return tournamentsList;
    }

    public void setTournamentsList(List<Tournaments> tournamentsList) {
        this.tournamentsList = tournamentsList;
    }

    public City getCityIdTournament() {
        return cityIdTournament;
    }

    public void setCityIdTournament(City cityIdTournament) {
        this.cityIdTournament = cityIdTournament;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTournament != null ? idTournament.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tournament)) {
            return false;
        }
        Tournament other = (Tournament) object;
        if ((this.idTournament == null && other.idTournament != null) || (this.idTournament != null && !this.idTournament.equals(other.idTournament))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        DateTools dt = new DateTools();
        return idTournament + ";" + dt.conversionDateToString(startDateTournament) + ";" + dt.conversionDateToString(endDateTournament) + ";" + roundsTournament + ";" + cityIdTournament.getIdCity();
    }    
}
