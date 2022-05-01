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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author joaoan2
 */
@Entity
@Table(name = "federation")
@NamedQueries({
    @NamedQuery(name = "Federation.findAll", query = "SELECT f FROM Federation f"),
    @NamedQuery(name = "Federation.findBySiglaFederation", query = "SELECT f FROM Federation f WHERE f.siglaFederation = :siglaFederation"),
    @NamedQuery(name = "Federation.findByNameFederation", query = "SELECT f FROM Federation f WHERE f.nameFederation = :nameFederation")})
public class Federation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sigla_federation")
    private String siglaFederation;
    @Basic(optional = false)
    @Column(name = "name_federation")
    private String nameFederation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "federationIdState")
    private List<State> stateList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "federationSiglaPlayer")
    private List<Player> playerList;

    public Federation() {
    }

    public Federation(String siglaFederation) {
        this.siglaFederation = siglaFederation;
    }

    public Federation(String siglaFederation, String nameFederation) {
        this.siglaFederation = siglaFederation;
        this.nameFederation = nameFederation;
    }

    public String getSiglaFederation() {
        return siglaFederation;
    }

    public void setSiglaFederation(String siglaFederation) {
        this.siglaFederation = siglaFederation;
    }

    public String getNameFederation() {
        return nameFederation;
    }

    public void setNameFederation(String nameFederation) {
        this.nameFederation = nameFederation;
    }

    public List<State> getStateList() {
        return stateList;
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (siglaFederation != null ? siglaFederation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Federation)) {
            return false;
        }
        Federation other = (Federation) object;
        if ((this.siglaFederation == null && other.siglaFederation != null) || (this.siglaFederation != null && !this.siglaFederation.equals(other.siglaFederation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return siglaFederation + ";" + nameFederation;
    }
    
}
