/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author tovo
 */
@Entity
@Table(name = "compra_has_produto")
@NamedQueries({
    @NamedQuery(name = "CompraHasProduto.findAll", query = "SELECT c FROM CompraHasProduto c"),
    @NamedQuery(name = "CompraHasProduto.findByCompraIdCompra", query = "SELECT c FROM CompraHasProduto c WHERE c.compraHasProdutoPK.compraIdCompra = :compraIdCompra"),
    @NamedQuery(name = "CompraHasProduto.findByProdutoIdProduto", query = "SELECT c FROM CompraHasProduto c WHERE c.compraHasProdutoPK.produtoIdProduto = :produtoIdProduto"),
    @NamedQuery(name = "CompraHasProduto.findByQuantidade", query = "SELECT c FROM CompraHasProduto c WHERE c.quantidade = :quantidade"),
    @NamedQuery(name = "CompraHasProduto.findByPrecoTotal", query = "SELECT c FROM CompraHasProduto c WHERE c.precoTotal = :precoTotal")})
public class CompraHasProduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompraHasProdutoPK compraHasProdutoPK;
    @Basic(optional = false)
    @Column(name = "quantidade")
    private String quantidade;
    @Basic(optional = false)
    @Column(name = "preco_total")
    private double precoTotal;
    @JoinColumn(name = "compra_id_compra", referencedColumnName = "id_compra", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Compra compra;
    @JoinColumn(name = "produto_id_produto", referencedColumnName = "id_produto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produto produto;

    public CompraHasProduto() {
    }

    public CompraHasProduto(CompraHasProdutoPK compraHasProdutoPK) {
        this.compraHasProdutoPK = compraHasProdutoPK;
    }

    public CompraHasProduto(CompraHasProdutoPK compraHasProdutoPK, String quantidade, double precoTotal) {
        this.compraHasProdutoPK = compraHasProdutoPK;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
    }

    public CompraHasProduto(int compraIdCompra, int produtoIdProduto) {
        this.compraHasProdutoPK = new CompraHasProdutoPK(compraIdCompra, produtoIdProduto);
    }

    public CompraHasProdutoPK getCompraHasProdutoPK() {
        return compraHasProdutoPK;
    }

    public void setCompraHasProdutoPK(CompraHasProdutoPK compraHasProdutoPK) {
        this.compraHasProdutoPK = compraHasProdutoPK;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (compraHasProdutoPK != null ? compraHasProdutoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraHasProduto)) {
            return false;
        }
        CompraHasProduto other = (CompraHasProduto) object;
        if ((this.compraHasProdutoPK == null && other.compraHasProdutoPK != null) || (this.compraHasProdutoPK != null && !this.compraHasProdutoPK.equals(other.compraHasProdutoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return compraHasProdutoPK + ";" + quantidade + ";" + precoTotal;
    }    
}
