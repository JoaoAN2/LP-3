package DAOs;

import Entidades.TournamentsPK;
import Entidades.Tournaments;
import java.util.ArrayList;
import java.util.List;

public class DAOTournaments extends DAOGenerico<Tournaments> {

    public DAOTournaments() {
        super(Tournaments.class);
    }

    public Tournaments obter(TournamentsPK tournamentsPK){
        return em.find(Tournaments.class, tournamentsPK);
    }

    public int autoIdTournaments() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.tournamentsIdTournament) FROM Tournamentse ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Tournaments> listInOrderTournamentsIdTournament() {
        return em.createQuery("SELECT e FROM Tournaments e ORDER BY e.tournamentsIdTournament").getResultList();
    }

    public List<Tournaments> listInOrderTournamentsIdPlayer() {
        return em.createQuery("SELECT e FROM Tournaments e ORDER BY e.tournamentsIdPlayer").getResultList();
    }

    public List<Tournaments> listInOrderPositionPlayer() {
        return em.createQuery("SELECT e FROM Tournaments e ORDER BY e.positionPlayer").getResultList();
    }

    public List<Tournaments> listInOrderPointsPlayer() {
        return em.createQuery("SELECT e FROM Tournaments e ORDER BY e.pointsPlayer").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Tournaments> lf;
        if (order.equals("tournamentsIdTournament")) {
            lf = listInOrderTournamentsIdTournament();
        } else if (order.equals("tournamentsIdPlayer")) {
            lf = listInOrderTournamentsIdPlayer();
        } else if (order.equals("positionPlayer")) {
            lf = listInOrderPositionPlayer();
        } else if (order.equals("pointsPlayer")) {
            lf = listInOrderPointsPlayer();
        } else {
            lf = listInOrderTournamentsIdTournament();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getTournamentsPK().toString());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOTournaments daoTournaments = new DAOTournaments();
        List<Tournaments> listaTournaments = daoTournaments.list();
        for (Tournaments tournaments : listaTournaments) {
            System.out.println(tournaments);
        }
    }
}
