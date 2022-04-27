/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author joaoan2
 */
@Entity
@Table(name = "cat_referee")
@NamedQueries({
    @NamedQuery(name = "CatReferee.findAll", query = "SELECT c FROM CatReferee c"),
    @NamedQuery(name = "CatReferee.findBySiglaCatReferee", query = "SELECT c FROM CatReferee c WHERE c.siglaCatReferee = :siglaCatReferee"),
    @NamedQuery(name = "CatReferee.findByNameCatReferee", query = "SELECT c FROM CatReferee c WHERE c.nameCatReferee = :nameCatReferee")})
public class CatReferee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sigla_cat_referee")
    private String siglaCatReferee;
    @Basic(optional = false)
    @Column(name = "name_cat_referee")
    private String nameCatReferee;

    public CatReferee() {
    }

    public CatReferee(String siglaCatReferee) {
        this.siglaCatReferee = siglaCatReferee;
    }

    public CatReferee(String siglaCatReferee, String nameCatReferee) {
        this.siglaCatReferee = siglaCatReferee;
        this.nameCatReferee = nameCatReferee;
    }

    public String getSiglaCatReferee() {
        return siglaCatReferee;
    }

    public void setSiglaCatReferee(String siglaCatReferee) {
        this.siglaCatReferee = siglaCatReferee;
    }

    public String getNameCatReferee() {
        return nameCatReferee;
    }

    public void setNameCatReferee(String nameCatReferee) {
        this.nameCatReferee = nameCatReferee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (siglaCatReferee != null ? siglaCatReferee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatReferee)) {
            return false;
        }
        CatReferee other = (CatReferee) object;
        if ((this.siglaCatReferee == null && other.siglaCatReferee != null) || (this.siglaCatReferee != null && !this.siglaCatReferee.equals(other.siglaCatReferee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  siglaCatReferee + ";" + nameCatReferee;
    }
    
}
