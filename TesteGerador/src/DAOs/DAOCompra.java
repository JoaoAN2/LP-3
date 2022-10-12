package DAOs;

import Entidades.Compra;
import java.util.ArrayList;
import java.util.List;

public class DAOCompra extends DAOGenerico<Compra> {

    public DAOCompra() {
        super(Compra.class);
    }

    public int autoIdCompra() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idCompra) FROM comprae ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Compra> listInOrderIdCompra() {
        return em.createQuery("SELECT e FROM compra e ORDER BY e.id_compra").getResultList();
    }

    public List<Compra> listInOrderDataCompra() {
        return em.createQuery("SELECT e FROM compra e ORDER BY e.data_compra").getResultList();
    }

    public List<Compra> listInOrderClientePessoaIdCompra() {
        return em.createQuery("SELECT e FROM compra e ORDER BY e.cliente_pessoa_id_compra").getResultList();
    }

    public List<Compra> listInOrderFuncionarioPessoaIdCompra() {
        return em.createQuery("SELECT e FROM compra e ORDER BY e.funcionario_pessoa_id_compra").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Compra> lf;
        if (order.equals("idCompra")) {
            lf = listInOrderIdCompra();
        } else if (order.equals("dataCompra")) {
            lf = listInOrderDataCompra();
        } else if (order.equals("clientePessoaIdCompra")) {
            lf = listInOrderClientePessoaIdCompra();
        } else if (order.equals("funcionarioPessoaIdCompra")) {
            lf = listInOrderFuncionarioPessoaIdCompra();
        } else {
            lf = listInOrderIdCompra();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdCompra() + "-" + lf.get(i).getDataCompra());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOCompra daoCompra = new DAOCompra();
        List<Compra> listaCompra = daoCompra.list();
        for (Compra compra : listaCompra) {
            System.out.println(compra);
        }
    }
}
