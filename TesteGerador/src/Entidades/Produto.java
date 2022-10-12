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
@Table(name = "produto")
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findByIdProduto", query = "SELECT p FROM Produto p WHERE p.idProduto = :idProduto"),
    @NamedQuery(name = "Produto.findByNomeProduto", query = "SELECT p FROM Produto p WHERE p.nomeProduto = :nomeProduto"),
    @NamedQuery(name = "Produto.findByEstoqueProduto", query = "SELECT p FROM Produto p WHERE p.estoqueProduto = :estoqueProduto"),
    @NamedQuery(name = "Produto.findByPrecoUnitarioProduto", query = "SELECT p FROM Produto p WHERE p.precoUnitarioProduto = :precoUnitarioProduto")})
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_produto")
    private Integer idProduto;
    @Basic(optional = false)
    @Column(name = "nome_produto")
    private String nomeProduto;
    @Basic(optional = false)
    @Column(name = "estoque_produto")
    private int estoqueProduto;
    @Basic(optional = false)
    @Column(name = "preco_unitario_produto")
    private double precoUnitarioProduto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<CompraHasProduto> compraHasProdutoList;

    public Produto() {
    }

    public Produto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Produto(Integer idProduto, String nomeProduto, int estoqueProduto, double precoUnitarioProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.estoqueProduto = estoqueProduto;
        this.precoUnitarioProduto = precoUnitarioProduto;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getEstoqueProduto() {
        return estoqueProduto;
    }

    public void setEstoqueProduto(int estoqueProduto) {
        this.estoqueProduto = estoqueProduto;
    }

    public double getPrecoUnitarioProduto() {
        return precoUnitarioProduto;
    }

    public void setPrecoUnitarioProduto(double precoUnitarioProduto) {
        this.precoUnitarioProduto = precoUnitarioProduto;
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
        hash += (idProduto != null ? idProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.idProduto == null && other.idProduto != null) || (this.idProduto != null && !this.idProduto.equals(other.idProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idProduto + ";" + nomeProduto + ";" + estoqueProduto + ";" + precoUnitarioProduto;
    }    
}
