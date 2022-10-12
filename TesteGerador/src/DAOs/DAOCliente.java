package DAOs;

import Entidades.Cliente;
import java.util.ArrayList;
import java.util.List;

public class DAOCliente extends DAOGenerico<Cliente> {

    public DAOCliente() {
        super(Cliente.class);
    }

    public int autoIdCliente() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.pessoaIdCliente) FROM clientee ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Cliente> listInOrderPessoaIdCliente() {
        return em.createQuery("SELECT e FROM cliente e ORDER BY e.pessoa_id_cliente").getResultList();
    }

    public List<Cliente> listInOrderCadastroCliente() {
        return em.createQuery("SELECT e FROM cliente e ORDER BY e.cadastro_cliente").getResultList();
    }

    public List<Cliente> listInOrderDesligamentoCliente() {
        return em.createQuery("SELECT e FROM cliente e ORDER BY e.desligamento_cliente").getResultList();
    }

    public List<Cliente> listInOrderDividaCliente() {
        return em.createQuery("SELECT e FROM cliente e ORDER BY e.divida_cliente").getResultList();
    }

    public List<Cliente> listInOrderTipoClienteIdCliente() {
        return em.createQuery("SELECT e FROM cliente e ORDER BY e.tipo_cliente_id_cliente").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Cliente> lf;
        if (order.equals("pessoaIdCliente")) {
            lf = listInOrderPessoaIdCliente();
        } else if (order.equals("cadastroCliente")) {
            lf = listInOrderCadastroCliente();
        } else if (order.equals("desligamentoCliente")) {
            lf = listInOrderDesligamentoCliente();
        } else if (order.equals("dividaCliente")) {
            lf = listInOrderDividaCliente();
        } else if (order.equals("tipoClienteIdCliente")) {
            lf = listInOrderTipoClienteIdCliente();
        } else {
            lf = listInOrderPessoaIdCliente();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getPessoaIdCliente() + "-" + lf.get(i).getCadastroCliente());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOCliente daoCliente = new DAOCliente();
        List<Cliente> listaCliente = daoCliente.list();
        for (Cliente cliente : listaCliente) {
            System.out.println(cliente);
        }
    }
}
