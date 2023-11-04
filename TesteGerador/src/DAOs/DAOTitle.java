package DAOs;

import Entidades.Title;
import java.util.ArrayList;
import java.util.List;

public class DAOTitle extends DAOGenerico<Title> {

    public DAOTitle() {
        super(Title.class);
    }

    public List<Title> listBySiglaTitle(String siglaTitle) {
        return em.createQuery("SELECT e FROM Title e WHERE e.siglaTitle LIKE :siglaTitle").setParameter("siglaTitle", "%" + siglaTitle + "%").getResultList();
    }

    public List<Title> listInOrderSiglaTitle() {
        return em.createQuery("SELECT e FROM Title e ORDER BY e.siglaTitle").getResultList();
    }

    public List<Title> listByNameTitle(String nameTitle) {
        return em.createQuery("SELECT e FROM Title e WHERE e.nameTitle LIKE :nameTitle").setParameter("nameTitle", "%" + nameTitle + "%").getResultList();
    }

    public List<Title> listInOrderNameTitle() {
        return em.createQuery("SELECT e FROM Title e ORDER BY e.nameTitle").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Title> lf;
        if (order.equals("siglaTitle")) {
            lf = listInOrderSiglaTitle();
        } else if (order.equals("nameTitle")) {
            lf = listInOrderNameTitle();
        } else {
            lf = listInOrderSiglaTitle();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSiglaTitle() + "-" + lf.get(i).getNameTitle());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOTitle daoTitle = new DAOTitle();
        List<Title> listaTitle = daoTitle.list();
        for (Title title : listaTitle) {
            System.out.println(title);
        }
    }
}
