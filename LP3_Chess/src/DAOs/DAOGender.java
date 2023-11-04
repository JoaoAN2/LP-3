package DAOs;

import Entidades.Gender;
import java.util.ArrayList;
import java.util.List;

public class DAOGender extends DAOGenerico<Gender> {

    public DAOGender() {
        super(Gender.class);
    }

    public List<Gender> listBySiglaGender(String siglaGender) {
        return em.createQuery("SELECT e FROM gender e WHERE e.sigla_gender LIKE :siglaGender").setParameter("siglaGender", "%" + siglaGender + "%").getResultList();
    }

    public List<Gender> listInOrderSiglaGender() {
        return em.createQuery("SELECT e FROM gender e ORDER BY e.sigla_gender").getResultList();
    }

    public List<Gender> listByFullGender(String fullGender) {
        return em.createQuery("SELECT e FROM gender e WHERE e.full_gender LIKE :fullGender").setParameter("fullGender", "%" + fullGender + "%").getResultList();
    }

    public List<Gender> listInOrderFullGender() {
        return em.createQuery("SELECT e FROM gender e ORDER BY e.full_gender").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Gender> lf;
        if (order.equals("siglaGender")) {
            lf = listInOrderSiglaGender();
        } else if (order.equals("fullGender")) {
            lf = listInOrderFullGender();
        } else {
            lf = listInOrderSiglaGender();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSiglaGender() + "-" + lf.get(i).getFullGender());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOGender daoGender = new DAOGender();
        List<Gender> listaGender = daoGender.list();
        for (Gender gender : listaGender) {
            System.out.println(gender);
        }
    }
}
