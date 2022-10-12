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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author tovo
 */
@Entity
@Table(name = "funcionario")
@NamedQueries({
    @NamedQuery(name = "Funcionario.findAll", query = "SELECT f FROM Funcionario f"),
    @NamedQuery(name = "Funcionario.findByPessoaIdFuncionario", query = "SELECT f FROM Funcionario f WHERE f.pessoaIdFuncionario = :pessoaIdFuncionario")})
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pessoa_id_funcionario")
    private Integer pessoaIdFuncionario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "funcionarioPessoaIdCompra")
    private List<Compra> compraList;
    @JoinColumn(name = "cargo_id_funcionario", referencedColumnName = "id_cargo")
    @ManyToOne(optional = false)
    private Cargo cargoIdFuncionario;
    @JoinColumn(name = "pessoa_id_funcionario", referencedColumnName = "id_pessoa", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Pessoa pessoa;

    public Funcionario() {
    }

    public Funcionario(Integer pessoaIdFuncionario) {
        this.pessoaIdFuncionario = pessoaIdFuncionario;
    }

    public Integer getPessoaIdFuncionario() {
        return pessoaIdFuncionario;
    }

    public void setPessoaIdFuncionario(Integer pessoaIdFuncionario) {
        this.pessoaIdFuncionario = pessoaIdFuncionario;
    }

    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    public Cargo getCargoIdFuncionario() {
        return cargoIdFuncionario;
    }

    public void setCargoIdFuncionario(Cargo cargoIdFuncionario) {
        this.cargoIdFuncionario = cargoIdFuncionario;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pessoaIdFuncionario != null ? pessoaIdFuncionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionario)) {
            return false;
        }
        Funcionario other = (Funcionario) object;
        if ((this.pessoaIdFuncionario == null && other.pessoaIdFuncionario != null) || (this.pessoaIdFuncionario != null && !this.pessoaIdFuncionario.equals(other.pessoaIdFuncionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return pessoaIdFuncionario + ";" + cargoIdFuncionario.getIdCargo();
    }    
}
