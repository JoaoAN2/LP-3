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
@Table(name = "gender")
@NamedQueries({
    @NamedQuery(name = "Gender.findAll", query = "SELECT g FROM Gender g"),
    @NamedQuery(name = "Gender.findBySiglaGender", query = "SELECT g FROM Gender g WHERE g.siglaGender = :siglaGender"),
    @NamedQuery(name = "Gender.findByFullGender", query = "SELECT g FROM Gender g WHERE g.fullGender = :fullGender")})
public class Gender implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sigla_gender")
    private String siglaGender;
    @Basic(optional = false)
    @Column(name = "full_gender")
    private String fullGender;

    public Gender() {
    }

    public Gender(String siglaGender) {
        this.siglaGender = siglaGender;
    }

    public Gender(String siglaGender, String fullGender) {
        this.siglaGender = siglaGender;
        this.fullGender = fullGender;
    }

    public String getSiglaGender() {
        return siglaGender;
    }

    public void setSiglaGender(String siglaGender) {
        this.siglaGender = siglaGender;
    }

    public String getFullGender() {
        return fullGender;
    }

    public void setFullGender(String fullGender) {
        this.fullGender = fullGender;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (siglaGender != null ? siglaGender.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gender)) {
            return false;
        }
        Gender other = (Gender) object;
        if ((this.siglaGender == null && other.siglaGender != null) || (this.siglaGender != null && !this.siglaGender.equals(other.siglaGender))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return siglaGender + ";" + fullGender;
    }
    
}
