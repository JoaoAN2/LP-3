package DAOs;

import Entidades.Tournament;
import java.util.ArrayList;
import java.util.List;

public class DAOTournament extends DAOGenerico<Tournament> {

    public DAOTournament() {
        super(Tournament.class);
    }

    public int autoIdTournament() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idTournament) FROM tournamente ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Tournament> listInOrderIdTournament() {
        return em.createQuery("SELECT e FROM tournament e ORDER BY e.id_tournament").getResultList();
    }

    public List<Tournament> listInOrderStartDateTournament() {
        return em.createQuery("SELECT e FROM tournament e ORDER BY e.start_date_tournament").getResultList();
    }

    public List<Tournament> listInOrderEndDateTournament() {
        return em.createQuery("SELECT e FROM tournament e ORDER BY e.end_date_tournament").getResultList();
    }

    public List<Tournament> listInOrderRoundsTournament() {
        return em.createQuery("SELECT e FROM tournament e ORDER BY e.rounds_tournament").getResultList();
    }

    public List<Tournament> listInOrderCityIdTournament() {
        return em.createQuery("SELECT e FROM tournament e ORDER BY e.city_id_tournament").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Tournament> lf;
        if (order.equals("idTournament")) {
            lf = listInOrderIdTournament();
        } else if (order.equals("startDateTournament")) {
            lf = listInOrderStartDateTournament();
        } else if (order.equals("endDateTournament")) {
            lf = listInOrderEndDateTournament();
        } else if (order.equals("roundsTournament")) {
            lf = listInOrderRoundsTournament();
        } else if (order.equals("cityIdTournament")) {
            lf = listInOrderCityIdTournament();
        } else {
            lf = listInOrderIdTournament();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdTournament() + "-" + lf.get(i).getStartDateTournament());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOTournament daoTournament = new DAOTournament();
        List<Tournament> listaTournament = daoTournament.list();
        for (Tournament tournament : listaTournament) {
            System.out.println(tournament);
        }
    }
}
