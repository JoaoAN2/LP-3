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
 * @author tovo
 */
@Entity
@Table(name = "tipo_cliente")
@NamedQueries({
    @NamedQuery(name = "TipoCliente.findAll", query = "SELECT t FROM TipoCliente t"),
    @NamedQuery(name = "TipoCliente.findByIdTipoCliente", query = "SELECT t FROM TipoCliente t WHERE t.idTipoCliente = :idTipoCliente"),
    @NamedQuery(name = "TipoCliente.findByNomeTipoCliente", query = "SELECT t FROM TipoCliente t WHERE t.nomeTipoCliente = :nomeTipoCliente")})
public class TipoCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_cliente")
    private Integer idTipoCliente;
    @Basic(optional = false)
    @Column(name = "nome_tipo_cliente")
    private String nomeTipoCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoClienteIdCliente")
    private List<Cliente> clienteList;

    public TipoCliente() {
    }

    public TipoCliente(Integer idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public TipoCliente(Integer idTipoCliente, String nomeTipoCliente) {
        this.idTipoCliente = idTipoCliente;
        this.nomeTipoCliente = nomeTipoCliente;
    }

    public Integer getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(Integer idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public String getNomeTipoCliente() {
        return nomeTipoCliente;
    }

    public void setNomeTipoCliente(String nomeTipoCliente) {
        this.nomeTipoCliente = nomeTipoCliente;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCliente != null ? idTipoCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCliente)) {
            return false;
        }
        TipoCliente other = (TipoCliente) object;
        if ((this.idTipoCliente == null && other.idTipoCliente != null) || (this.idTipoCliente != null && !this.idTipoCliente.equals(other.idTipoCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idTipoCliente + ";" + nomeTipoCliente;
    }    
}
