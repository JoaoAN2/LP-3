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
@Table(name = "Trabalhador")
@NamedQueries({
    @NamedQuery(name = "Trabalhador.findAll", query = "SELECT t FROM Trabalhador t"),
    @NamedQuery(name = "Trabalhador.findByCpf", query = "SELECT t FROM Trabalhador t WHERE t.cpf = :cpf"),
    @NamedQuery(name = "Trabalhador.findByAposentado", query = "SELECT t FROM Trabalhador t WHERE t.aposentado = :aposentado"),
    @NamedQuery(name = "Trabalhador.findByNome", query = "SELECT t FROM Trabalhador t WHERE t.nome = :nome"),
    @NamedQuery(name = "Trabalhador.findBySalario", query = "SELECT t FROM Trabalhador t WHERE t.salario = :salario")})
public class Trabalhador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "aposentado")
    private Short aposentado;
    @Column(name = "nome")
    private String nome;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salario")
    private Double salario;

    public Trabalhador() {
    }

    public Trabalhador(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Short getAposentado() {
        return aposentado;
    }

    public void setAposentado(Short aposentado) {
        this.aposentado = aposentado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpf != null ? cpf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trabalhador)) {
            return false;
        }
        Trabalhador other = (Trabalhador) object;
        if ((this.cpf == null && other.cpf != null) || (this.cpf != null && !this.cpf.equals(other.cpf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cpf + ";" + aposentado + ";" + nome + ";" + salario;
    }
    
}
