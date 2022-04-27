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
@Table(name = "title")
@NamedQueries({
    @NamedQuery(name = "Title.findAll", query = "SELECT t FROM Title t"),
    @NamedQuery(name = "Title.findByNameTitle", query = "SELECT t FROM Title t WHERE t.nameTitle = :nameTitle"),
    @NamedQuery(name = "Title.findBySiglaTitle", query = "SELECT t FROM Title t WHERE t.siglaTitle = :siglaTitle")})
public class Title implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "name_title")
    private String nameTitle;
    @Id
    @Basic(optional = false)
    @Column(name = "sigla_title")
    private String siglaTitle;

    public Title() {
    }

    public Title(String siglaTitle) {
        this.siglaTitle = siglaTitle;
    }

    public Title(String siglaTitle, String nameTitle) {
        this.siglaTitle = siglaTitle;
        this.nameTitle = nameTitle;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public String getSiglaTitle() {
        return siglaTitle;
    }

    public void setSiglaTitle(String siglaTitle) {
        this.siglaTitle = siglaTitle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (siglaTitle != null ? siglaTitle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Title)) {
            return false;
        }
        Title other = (Title) object;
        if ((this.siglaTitle == null && other.siglaTitle != null) || (this.siglaTitle != null && !this.siglaTitle.equals(other.siglaTitle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return siglaTitle + ";" + nameTitle;
    }
    
}
