package DAOs;

import Entidades.Cargo;
import java.util.ArrayList;
import java.util.List;

public class DAOCargo extends DAOGenerico<Cargo> {

    public DAOCargo() {
        super(Cargo.class);
    }

    public int autoIdCargo() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idCargo) FROM cargoe ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Cargo> listInOrderIdCargo() {
        return em.createQuery("SELECT e FROM cargo e ORDER BY e.id_cargo").getResultList();
    }

    public List<Cargo> listByNomeCargo(String nomeCargo) {
        return em.createQuery("SELECT e FROM cargo e WHERE e.nome_cargo LIKE :nomeCargo").setParameter("nomeCargo", "%" + nomeCargo + "%").getResultList();
    }

    public List<Cargo> listInOrderNomeCargo() {
        return em.createQuery("SELECT e FROM cargo e ORDER BY e.nome_cargo").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Cargo> lf;
        if (order.equals("idCargo")) {
            lf = listInOrderIdCargo();
        } else if (order.equals("nomeCargo")) {
            lf = listInOrderNomeCargo();
        } else {
            lf = listInOrderIdCargo();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdCargo() + "-" + lf.get(i).getNomeCargo());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOCargo daoCargo = new DAOCargo();
        List<Cargo> listaCargo = daoCargo.list();
        for (Cargo cargo : listaCargo) {
            System.out.println(cargo);
        }
    }
}
