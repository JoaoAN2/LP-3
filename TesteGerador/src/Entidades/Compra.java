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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tovo
 */
@Entity
@Table(name = "compra")
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findByIdCompra", query = "SELECT c FROM Compra c WHERE c.idCompra = :idCompra"),
    @NamedQuery(name = "Compra.findByDataCompra", query = "SELECT c FROM Compra c WHERE c.dataCompra = :dataCompra")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_compra")
    private Integer idCompra;
    @Basic(optional = false)
    @Column(name = "data_compra")
    @Temporal(TemporalType.DATE)
    private Date dataCompra;
    @JoinColumn(name = "cliente_pessoa_id_compra", referencedColumnName = "pessoa_id_cliente")
    @ManyToOne(optional = false)
    private Cliente clientePessoaIdCompra;
    @JoinColumn(name = "funcionario_pessoa_id_compra", referencedColumnName = "pessoa_id_funcionario")
    @ManyToOne(optional = false)
    private Funcionario funcionarioPessoaIdCompra;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compra")
    private List<CompraHasProduto> compraHasProdutoList;

    public Compra() {
    }

    public Compra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Compra(Integer idCompra, Date dataCompra) {
        this.idCompra = idCompra;
        this.dataCompra = dataCompra;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Cliente getClientePessoaIdCompra() {
        return clientePessoaIdCompra;
    }

    public void setClientePessoaIdCompra(Cliente clientePessoaIdCompra) {
        this.clientePessoaIdCompra = clientePessoaIdCompra;
    }

    public Funcionario getFuncionarioPessoaIdCompra() {
        return funcionarioPessoaIdCompra;
    }

    public void setFuncionarioPessoaIdCompra(Funcionario funcionarioPessoaIdCompra) {
        this.funcionarioPessoaIdCompra = funcionarioPessoaIdCompra;
    }

    public List<CompraHasProduto> getCompraHasProdutoList() {
        return compraHasProdutoList;
    }

    public void setCompraHasProdutoList(List<CompraHasProduto> compraHasProdutoList) {
        this.compraHasProdutoList = compraHasProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        DateTools dt = new DateTools();
        return idCompra + ";" + dt.conversionDateToString(dataCompra) + ";" + clientePessoaIdCompra.getPessoaIdCliente() + ";" + funcionarioPessoaIdCompra.getPessoaIdFuncionario();
    }    
}
