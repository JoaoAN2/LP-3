package DAOs;

import Entidades.TipoCliente;
import java.util.ArrayList;
import java.util.List;

public class DAOTipoCliente extends DAOGenerico<TipoCliente> {

    public DAOTipoCliente() {
        super(TipoCliente.class);
    }

    public int autoIdTipoCliente() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idTipoCliente) FROM tipo_clientee ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<TipoCliente> listInOrderIdTipoCliente() {
        return em.createQuery("SELECT e FROM tipo_cliente e ORDER BY e.id_tipo_cliente").getResultList();
    }

    public List<TipoCliente> listByNomeTipoCliente(String nomeTipoCliente) {
        return em.createQuery("SELECT e FROM tipo_cliente e WHERE e.nome_tipo_cliente LIKE :nomeTipoCliente").setParameter("nomeTipoCliente", "%" + nomeTipoCliente + "%").getResultList();
    }

    public List<TipoCliente> listInOrderNomeTipoCliente() {
        return em.createQuery("SELECT e FROM tipo_cliente e ORDER BY e.nome_tipo_cliente").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<TipoCliente> lf;
        if (order.equals("idTipoCliente")) {
            lf = listInOrderIdTipoCliente();
        } else if (order.equals("nomeTipoCliente")) {
            lf = listInOrderNomeTipoCliente();
        } else {
            lf = listInOrderIdTipoCliente();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdTipoCliente() + "-" + lf.get(i).getNomeTipoCliente());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOTipoCliente daoTipoCliente = new DAOTipoCliente();
        List<TipoCliente> listaTipoCliente = daoTipoCliente.list();
        for (TipoCliente tipoCliente : listaTipoCliente) {
            System.out.println(tipoCliente);
        }
    }
}
