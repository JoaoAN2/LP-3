package DAOs;

import Entidades.Federation;
import GUIs.FederationGUI;
import java.util.ArrayList;
import java.util.List;

public class DAOFederation extends DAOGenerico<Federation> {

    public DAOFederation() {
        super(Federation.class);
    }

    public List<Federation> listBySiglaFederation(String siglaFederation) {
        return em.createQuery("SELECT e FROM federation e WHERE e.sigla_federation LIKE :siglaFederation").setParameter("siglaFederation", "%" + siglaFederation + "%").getResultList();
    }

    public List<Federation> listInOrderSiglaFederation() {
        return em.createQuery("SELECT e FROM federation e ORDER BY e.sigla_federation").getResultList();
    }

    public List<Federation> listByNameFederation(String nameFederation) {
        return em.createQuery("SELECT e FROM federation e WHERE e.name_federation LIKE :nameFederation").setParameter("nameFederation", "%" + nameFederation + "%").getResultList();
    }

    public List<Federation> listInOrderNameFederation() {
        return em.createQuery("SELECT e FROM federation e ORDER BY e.name_federation").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Federation> lf;
        if (order.equals("siglaFederation")) {
            lf = listInOrderSiglaFederation();
        } else if (order.equals("nameFederation")) {
            lf = listInOrderNameFederation();
        } else {
            lf = listInOrderSiglaFederation();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSiglaFederation() + "-" + lf.get(i).getNameFederation());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOFederation daoFederation = new DAOFederation();
        List<Federation> listaFederation = daoFederation.list();
        FederationGUI federationGUI = new FederationGUI();
        for (Federation federation : listaFederation) {
            System.out.println(federation);
        }
    }
}
