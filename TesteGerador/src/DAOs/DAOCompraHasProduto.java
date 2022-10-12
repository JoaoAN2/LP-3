package DAOs;

import Entidades.CompraHasProduto;
import Entidades.CompraHasProdutoPK;
import java.util.ArrayList;
import java.util.List;

public class DAOCompraHasProduto extends DAOGenerico<CompraHasProduto> {

    public DAOCompraHasProduto() {
        super(CompraHasProduto.class);
    }
    
    public CompraHasProduto obter(CompraHasProdutoPK compraHasProdutoPK) {
        return em.find(CompraHasProduto.class, compraHasProdutoPK);
    }

    public int autoIdCompraHasProduto() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.compraIdCompra) FROM compra_has_produtoe ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<CompraHasProduto> listInOrderCompraIdCompra() {
        return em.createQuery("SELECT e FROM compra_has_produto e ORDER BY e.compra_id_compra").getResultList();
    }

    public List<CompraHasProduto> listInOrderProdutoIdProduto() {
        return em.createQuery("SELECT e FROM compra_has_produto e ORDER BY e.produto_id_produto").getResultList();
    }

    public List<CompraHasProduto> listByQuantidade(String quantidade) {
        return em.createQuery("SELECT e FROM compra_has_produto e WHERE e.quantidade LIKE :quantidade").setParameter("quantidade", "%" + quantidade + "%").getResultList();
    }

    public List<CompraHasProduto> listInOrderQuantidade() {
        return em.createQuery("SELECT e FROM compra_has_produto e ORDER BY e.quantidade").getResultList();
    }

    public List<CompraHasProduto> listInOrderPrecoTotal() {
        return em.createQuery("SELECT e FROM compra_has_produto e ORDER BY e.preco_total").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<CompraHasProduto> lf;
        if (order.equals("compraIdCompra")) {
            lf = listInOrderCompraIdCompra();
        } else if (order.equals("produtoIdProduto")) {
            lf = listInOrderProdutoIdProduto();
        } else if (order.equals("quantidade")) {
            lf = listInOrderQuantidade();
        } else if (order.equals("precoTotal")) {
            lf = listInOrderPrecoTotal();
        } else {
            lf = listInOrderCompraIdCompra();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getCompra() + "-" + lf.get(i).getProduto());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOCompraHasProduto daoCompraHasProduto = new DAOCompraHasProduto();
        List<CompraHasProduto> listaCompraHasProduto = daoCompraHasProduto.list();
        for (CompraHasProduto compraHasProduto : listaCompraHasProduto) {
            System.out.println(compraHasProduto);
        }
    }
}
