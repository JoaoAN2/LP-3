package DAOs;

import Entidades.Referee;
import java.util.ArrayList;
import java.util.List;

public class DAOReferee extends DAOGenerico<Referee> {

    public DAOReferee() {
        super(Referee.class);
    }

    public int autoIdReferee() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.playerIdPlayer) FROM refereee ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Referee> listInOrderPlayerIdPlayer() {
        return em.createQuery("SELECT e FROM referee e ORDER BY e.player_id_player").getResultList();
    }

    public List<Referee> listByCatRefereeIdReferee(String catRefereeIdReferee) {
        return em.createQuery("SELECT e FROM referee e WHERE e.cat_referee_id_referee LIKE :catRefereeIdReferee").setParameter("catRefereeIdReferee", "%" + catRefereeIdReferee + "%").getResultList();
    }

    public List<Referee> listInOrderCatRefereeIdReferee() {
        return em.createQuery("SELECT e FROM referee e ORDER BY e.cat_referee_id_referee").getResultList();
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
