package DAOs;

import Entidades.City;
import java.util.ArrayList;
import java.util.List;

public class DAOCity extends DAOGenerico<City> {

    public DAOCity() {
        super(City.class);
    }

    public int autoIdCity() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idCity) FROM citye ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<City> listInOrderIdCity() {
        return em.createQuery("SELECT e FROM city e ORDER BY e.id_city").getResultList();
    }

    public List<City> listByNameCity(String nameCity) {
        return em.createQuery("SELECT e FROM city e WHERE e.name_city LIKE :nameCity").setParameter("nameCity", "%" + nameCity + "%").getResultList();
    }

    public List<City> listInOrderNameCity() {
        return em.createQuery("SELECT e FROM city e ORDER BY e.name_city").getResultList();
    }

    public List<City> listByStateSiglaCity(String stateSiglaCity) {
        return em.createQuery("SELECT e FROM city e WHERE e.state_sigla_city LIKE :stateSiglaCity").setParameter("stateSiglaCity", "%" + stateSiglaCity + "%").getResultList();
    }

    public List<City> listInOrderStateSiglaCity() {
        return em.createQuery("SELECT e FROM city e ORDER BY e.state_sigla_city").getResultList();
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
