package DAOs;

import Entidades.CatReferee;
import java.util.ArrayList;
import java.util.List;

public class DAOCatReferee extends DAOGenerico<CatReferee> {

    public DAOCatReferee() {
        super(CatReferee.class);
    }

    public List<CatReferee> listBySiglaCatReferee(String siglaCatReferee) {
        return em.createQuery("SELECT e FROM cat_referee e WHERE e.sigla_cat_referee LIKE :siglaCatReferee").setParameter("siglaCatReferee", "%" + siglaCatReferee + "%").getResultList();
    }

    public List<CatReferee> listInOrderSiglaCatReferee() {
        return em.createQuery("SELECT e FROM cat_referee e ORDER BY e.sigla_cat_referee").getResultList();
    }

    public List<CatReferee> listByNameCatReferee(String nameCatReferee) {
        return em.createQuery("SELECT e FROM cat_referee e WHERE e.name_cat_referee LIKE :nameCatReferee").setParameter("nameCatReferee", "%" + nameCatReferee + "%").getResultList();
    }

    public List<CatReferee> listInOrderNameCatReferee() {
        return em.createQuery("SELECT e FROM cat_referee e ORDER BY e.name_cat_referee").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<CatReferee> lf;
        if (order.equals("siglaCatReferee")) {
            lf = listInOrderSiglaCatReferee();
        } else if (order.equals("nameCatReferee")) {
            lf = listInOrderNameCatReferee();
        } else {
            lf = listInOrderSiglaCatReferee();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSiglaCatReferee() + "-" + lf.get(i).getNameCatReferee());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOCatReferee daoCatReferee = new DAOCatReferee();
        List<CatReferee> listaCatReferee = daoCatReferee.list();
        for (CatReferee catReferee : listaCatReferee) {
            System.out.println(catReferee);
        }
    }
}
