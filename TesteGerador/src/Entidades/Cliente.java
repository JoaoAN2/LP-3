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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tovo
 */
@Entity
@Table(name = "cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByPessoaIdCliente", query = "SELECT c FROM Cliente c WHERE c.pessoaIdCliente = :pessoaIdCliente"),
    @NamedQuery(name = "Cliente.findByCadastroCliente", query = "SELECT c FROM Cliente c WHERE c.cadastroCliente = :cadastroCliente"),
    @NamedQuery(name = "Cliente.findByDesligamentoCliente", query = "SELECT c FROM Cliente c WHERE c.desligamentoCliente = :desligamentoCliente"),
    @NamedQuery(name = "Cliente.findByDividaCliente", query = "SELECT c FROM Cliente c WHERE c.dividaCliente = :dividaCliente")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pessoa_id_cliente")
    private Integer pessoaIdCliente;
    @Basic(optional = false)
    @Column(name = "cadastro_cliente")
    @Temporal(TemporalType.DATE)
    private Date cadastroCliente;
    @Basic(optional = false)
    @Column(name = "desligamento_cliente")
    @Temporal(TemporalType.DATE)
    private Date desligamentoCliente;
    @Basic(optional = false)
    @Column(name = "divida_cliente")
    private double dividaCliente;
    @JoinColumn(name = "pessoa_id_cliente", referencedColumnName = "id_pessoa", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Pessoa pessoa;
    @JoinColumn(name = "tipo_cliente_id_cliente", referencedColumnName = "id_tipo_cliente")
    @ManyToOne(optional = false)
    private TipoCliente tipoClienteIdCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientePessoaIdCompra")
    private List<Compra> compraList;

    public Cliente() {
    }

    public Cliente(Integer pessoaIdCliente) {
        this.pessoaIdCliente = pessoaIdCliente;
    }

    public Cliente(Integer pessoaIdCliente, Date cadastroCliente, Date desligamentoCliente, double dividaCliente) {
        this.pessoaIdCliente = pessoaIdCliente;
        this.cadastroCliente = cadastroCliente;
        this.desligamentoCliente = desligamentoCliente;
        this.dividaCliente = dividaCliente;
    }

    public Integer getPessoaIdCliente() {
        return pessoaIdCliente;
    }

    public void setPessoaIdCliente(Integer pessoaIdCliente) {
        this.pessoaIdCliente = pessoaIdCliente;
    }

    public Date getCadastroCliente() {
        return cadastroCliente;
    }

    public void setCadastroCliente(Date cadastroCliente) {
        this.cadastroCliente = cadastroCliente;
    }

    public Date getDesligamentoCliente() {
        return desligamentoCliente;
    }

    public void setDesligamentoCliente(Date desligamentoCliente) {
        this.desligamentoCliente = desligamentoCliente;
    }

    public double getDividaCliente() {
        return dividaCliente;
    }

    public void setDividaCliente(double dividaCliente) {
        this.dividaCliente = dividaCliente;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TipoCliente getTipoClienteIdCliente() {
        return tipoClienteIdCliente;
    }

    public void setTipoClienteIdCliente(TipoCliente tipoClienteIdCliente) {
        this.tipoClienteIdCliente = tipoClienteIdCliente;
    }

    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pessoaIdCliente != null ? pessoaIdCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.pessoaIdCliente == null && other.pessoaIdCliente != null) || (this.pessoaIdCliente != null && !this.pessoaIdCliente.equals(other.pessoaIdCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        DateTools dt = new DateTools();
        return pessoaIdCliente + ";" + dt.conversionDateToString(cadastroCliente) + ";" + dt.conversionDateToString(desligamentoCliente) + ";" + dividaCliente + ";" + tipoClienteIdCliente.getIdTipoCliente();
    }    
}
