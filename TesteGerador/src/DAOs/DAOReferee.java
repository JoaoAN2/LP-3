package DAOs;

import Entidades.Referee;
import java.util.ArrayList;
import java.util.List;

public class DAOReferee extends DAOGenerico<Referee> {

    public DAOReferee() {
        super(Referee.class);
    }

    public int autoIdReferee() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.playerIdPlayer) FROM Refereee ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Referee> listInOrderPlayerIdPlayer() {
        return em.createQuery("SELECT e FROM Referee e ORDER BY e.playerIdPlayer").getResultList();
    }

    public List<Referee> listByCatRefereeIdReferee(String catRefereeIdReferee) {
        return em.createQuery("SELECT e FROM Referee e WHERE e.catRefereeIdReferee LIKE :catRefereeIdReferee").setParameter("catRefereeIdReferee", "%" + catRefereeIdReferee + "%").getResultList();
    }

    public List<Referee> listInOrderCatRefereeIdReferee() {
        return em.createQuery("SELECT e FROM Referee e ORDER BY e.catRefereeIdReferee").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Referee> lf;
        if (order.equals("playerIdPlayer")) {
            lf = listInOrderPlayerIdPlayer();
        } else if (order.equals("catRefereeIdReferee")) {
            lf = listInOrderCatRefereeIdReferee();
        } else {
            lf = listInOrderPlayerIdPlayer();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getPlayerIdPlayer() + "-" + lf.get(i).getCatRefereeIdReferee());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOReferee daoReferee = new DAOReferee();
        List<Referee> listaReferee = daoReferee.list();
        for (Referee referee : listaReferee) {
            System.out.println(referee);
        }
    }
}
