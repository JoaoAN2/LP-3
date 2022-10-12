package DAOs;

import Entidades.Produto;
import java.util.ArrayList;
import java.util.List;

public class DAOProduto extends DAOGenerico<Produto> {

    public DAOProduto() {
        super(Produto.class);
    }

    public int autoIdProduto() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idProduto) FROM produtoe ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Produto> listInOrderIdProduto() {
        return em.createQuery("SELECT e FROM produto e ORDER BY e.id_produto").getResultList();
    }

    public List<Produto> listByNomeProduto(String nomeProduto) {
        return em.createQuery("SELECT e FROM produto e WHERE e.nome_produto LIKE :nomeProduto").setParameter("nomeProduto", "%" + nomeProduto + "%").getResultList();
    }

    public List<Produto> listInOrderNomeProduto() {
        return em.createQuery("SELECT e FROM produto e ORDER BY e.nome_produto").getResultList();
    }

    public List<Produto> listInOrderEstoqueProduto() {
        return em.createQuery("SELECT e FROM produto e ORDER BY e.estoque_produto").getResultList();
    }

    public List<Produto> listInOrderPrecoUnitarioProduto() {
        return em.createQuery("SELECT e FROM produto e ORDER BY e.preco_unitario_produto").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Produto> lf;
        if (order.equals("idProduto")) {
            lf = listInOrderIdProduto();
        } else if (order.equals("nomeProduto")) {
            lf = listInOrderNomeProduto();
        } else if (order.equals("estoqueProduto")) {
            lf = listInOrderEstoqueProduto();
        } else if (order.equals("precoUnitarioProduto")) {
            lf = listInOrderPrecoUnitarioProduto();
        } else {
            lf = listInOrderIdProduto();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdProduto() + "-" + lf.get(i).getNomeProduto());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOProduto daoProduto = new DAOProduto();
        List<Produto> listaProduto = daoProduto.list();
        for (Produto produto : listaProduto) {
            System.out.println(produto);
        }
    }
}
