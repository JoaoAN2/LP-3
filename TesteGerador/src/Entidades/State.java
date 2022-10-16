/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * @author joaoan2
 */
@Entity
@Table(name = "state")
@NamedQueries({
    @NamedQuery(name = "State.findAll", query = "SELECT s FROM State s"),
    @NamedQuery(name = "State.findBySiglaState", query = "SELECT s FROM State s WHERE s.siglaState = :siglaState"),
    @NamedQuery(name = "State.findByNameState", query = "SELECT s FROM State s WHERE s.nameState = :nameState")})
public class State implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sigla_state")
    private String siglaState;
    @Basic(optional = false)
    @Column(name = "name_state")
    private String nameState;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateSiglaCity")
    private List<City> cityList;
    @JoinColumn(name = "federation_id_state", referencedColumnName = "sigla_federation")
    @ManyToOne(optional = false)
    private Federation federationIdState;

    public State() {
    }

    public State(String siglaState) {
        this.siglaState = siglaState;
    }

    public State(String siglaState, String nameState) {
        this.siglaState = siglaState;
        this.nameState = nameState;
    }

    public String getSiglaState() {
        return siglaState;
    }

    public void setSiglaState(String siglaState) {
        this.siglaState = siglaState;
    }

    public String getNameState() {
        return nameState;
    }

    public void setNameState(String nameState) {
        this.nameState = nameState;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public Federation getFederationIdState() {
        return federationIdState;
    }

    public void setFederationIdState(Federation federationIdState) {
        this.federationIdState = federationIdState;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (siglaState != null ? siglaState.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof State)) {
            return false;
        }
        State other = (State) object;
        if ((this.siglaState == null && other.siglaState != null) || (this.siglaState != null && !this.siglaState.equals(other.siglaState))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return siglaState + ";" + nameState + ";" + federationIdState.getSiglaFederation();
    }    
}
