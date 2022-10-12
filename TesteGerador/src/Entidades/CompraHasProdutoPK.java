/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author tovo
 */
@Embeddable
public class CompraHasProdutoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "compra_id_compra")
    private int compraIdCompra;
    @Basic(optional = false)
    @Column(name = "produto_id_produto")
    private int produtoIdProduto;

    public CompraHasProdutoPK() {
    }

    public CompraHasProdutoPK(int compraIdCompra, int produtoIdProduto) {
        this.compraIdCompra = compraIdCompra;
        this.produtoIdProduto = produtoIdProduto;
    }

    public int getCompraIdCompra() {
        return compraIdCompra;
    }

    public void setCompraIdCompra(int compraIdCompra) {
        this.compraIdCompra = compraIdCompra;
    }

    public int getProdutoIdProduto() {
        return produtoIdProduto;
    }

    public void setProdutoIdProduto(int produtoIdProduto) {
        this.produtoIdProduto = produtoIdProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) compraIdCompra;
        hash += (int) produtoIdProduto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompraHasProdutoPK)) {
            return false;
        }
        CompraHasProdutoPK other = (CompraHasProdutoPK) object;
        if (this.compraIdCompra != other.compraIdCompra) {
            return false;
        }
        if (this.produtoIdProduto != other.produtoIdProduto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return compraIdCompra + ";" + produtoIdProduto;
    }
    
}
