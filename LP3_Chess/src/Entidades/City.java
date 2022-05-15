package Entidades;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author JoaoAN2
 */
@Entity
@Table(name = "city")
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c"),
    @NamedQuery(name = "City.findByIdCity", query = "SELECT c FROM City c WHERE c.idCity = :idCity"),
    @NamedQuery(name = "City.findByNameCity", query = "SELECT c FROM City c WHERE c.nameCity = :nameCity")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_city")
    private Integer idCity;
    @Basic(optional = false)
    @Column(name = "name_city")
    private String nameCity;
    @JoinColumn(name = "state_sigla_city", referencedColumnName = "sigla_state")
    @ManyToOne(optional = false)
    private State stateSiglaCity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cityIdTournament")
    private List<Tournament> tournamentList;

    public City() {
    }

    public City(Integer idCity) {
        this.idCity = idCity;
    }

    public City(Integer idCity, String nameCity) {
        this.idCity = idCity;
        this.nameCity = nameCity;
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public State getStateSiglaCity() {
        return stateSiglaCity;
    }

    public void setStateSiglaCity(State stateSiglaCity) {
        this.stateSiglaCity = stateSiglaCity;
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCity != null ? idCity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.idCity == null && other.idCity != null) || (this.idCity != null && !this.idCity.equals(other.idCity))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.City[ idCity=" + idCity + " ]";
    }
    
}
