package DAOs;

import Entidades.City;
import java.util.ArrayList;
import java.util.List;

public class DAOCity extends DAOGenerico<City> {

    public DAOCity() {
        super(City.class);
    }

    public int autoIdCity() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idCity) FROM Citye ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<City> listInOrderIdCity() {
        return em.createQuery("SELECT e FROM City e ORDER BY e.idCity").getResultList();
    }

    public List<City> listByNameCity(String nameCity) {
        return em.createQuery("SELECT e FROM City e WHERE e.nameCity LIKE :nameCity").setParameter("nameCity", "%" + nameCity + "%").getResultList();
    }

    public List<City> listInOrderNameCity() {
        return em.createQuery("SELECT e FROM City e ORDER BY e.nameCity").getResultList();
    }

    public List<City> listByStateSiglaCity(String stateSiglaCity) {
        return em.createQuery("SELECT e FROM City e WHERE e.stateSiglaCity LIKE :stateSiglaCity").setParameter("stateSiglaCity", "%" + stateSiglaCity + "%").getResultList();
    }

    public List<City> listInOrderStateSiglaCity() {
        return em.createQuery("SELECT e FROM City e ORDER BY e.stateSiglaCity").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<City> lf;
        if (order.equals("idCity")) {
            lf = listInOrderIdCity();
        } else if (order.equals("nameCity")) {
            lf = listInOrderNameCity();
        } else if (order.equals("stateSiglaCity")) {
            lf = listInOrderStateSiglaCity();
        } else {
            lf = listInOrderIdCity();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdCity() + "-" + lf.get(i).getNameCity());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOCity daoCity = new DAOCity();
        List<City> listaCity = daoCity.list();
        for (City city : listaCity) {
            System.out.println(city);
        }
    }
}
