package DAOs;

import Entidades.CatReferee;
import java.util.ArrayList;
import java.util.List;

public class DAOCatReferee extends DAOGenerico<CatReferee> {

    private List<CatReferee> lista = new ArrayList<>();

    public DAOCatReferee() {
        super(CatReferee.class);
    }

    public int autoIdCatReferee() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.id_cat_referee) FROM cat_referee e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<CatReferee> listByNome(String nome) {
        return em.createQuery("SELECT e FROM cat_referee e WHERE e.id_cat_referee) LIKE :nome_cat_referee").setParameter("nome_cat_referee", "%" + nome + "%").getResultList();
    }

    public List<CatReferee> listById(int id) {
        return em.createQuery("SELECT e FROM cat_referee + e WHERE e.id_cat_referee= :id_cat_referee").setParameter("id_cat_referee", id).getResultList();
    }

    public List<CatReferee> listInOrderNome() {
        return em.createQuery("SELECT e FROM cat_referee e ORDER BY e.nome_cat_referee").getResultList();
    }

    public List<CatReferee> listInOrderId() {
        return em.createQuery("SELECT e FROM cat_referee e ORDER BY e.id_cat_referee").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<CatReferee> lf;
        if (qualOrdem.equals("sigla")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSiglaCatReferee()+ "-" + lf.get(i).getNameCatReferee());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOCatReferee daoCatReferee = new DAOCatReferee();
        List<CatReferee> listaCatReferee = daoCatReferee.list();
        for (CatReferee catReferee : listaCatReferee) {
            System.out.println(catReferee.getSiglaCatReferee()+ "-" + catReferee.getNameCatReferee());
        }
    }
}
