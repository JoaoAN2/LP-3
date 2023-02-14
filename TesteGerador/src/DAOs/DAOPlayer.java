package DAOs;

import Entidades.Player;
import java.util.ArrayList;
import java.util.List;

public class DAOPlayer extends DAOGenerico<Player> {

    public DAOPlayer() {
        super(Player.class);
    }

    public int autoIdPlayer() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idPlayer) FROM playere ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Player> listInOrderIdPlayer() {
        return em.createQuery("SELECT e FROM player e ORDER BY e.id_player").getResultList();
    }

    public List<Player> listByNamePlayer(String namePlayer) {
        return em.createQuery("SELECT e FROM player e WHERE e.name_player LIKE :namePlayer").setParameter("namePlayer", "%" + namePlayer + "%").getResultList();
    }

    public List<Player> listInOrderNamePlayer() {
        return em.createQuery("SELECT e FROM player e ORDER BY e.name_player").getResultList();
    }

    public List<Player> listInOrderPointsPlayer() {
        return em.createQuery("SELECT e FROM player e ORDER BY e.points_player").getResultList();
    }

    public List<Player> listInOrderBirthdayPlayer() {
        return em.createQuery("SELECT e FROM player e ORDER BY e.birthday_player").getResultList();
    }

    public List<Player> listByFederationSiglaPlayer(String federationSiglaPlayer) {
        return em.createQuery("SELECT e FROM player e WHERE e.federation_sigla_player LIKE :federationSiglaPlayer").setParameter("federationSiglaPlayer", "%" + federationSiglaPlayer + "%").getResultList();
    }

    public List<Player> listInOrderFederationSiglaPlayer() {
        return em.createQuery("SELECT e FROM player e ORDER BY e.federation_sigla_player").getResultList();
    }

    public List<Player> listByGenderSiglaPlayer(String genderSiglaPlayer) {
        return em.createQuery("SELECT e FROM player e WHERE e.gender_sigla_player LIKE :genderSiglaPlayer").setParameter("genderSiglaPlayer", "%" + genderSiglaPlayer + "%").getResultList();
    }

    public List<Player> listInOrderGenderSiglaPlayer() {
        return em.createQuery("SELECT e FROM player e ORDER BY e.gender_sigla_player").getResultList();
    }

    public List<Player> listByTitleSiglaPlayer(String titleSiglaPlayer) {
        return em.createQuery("SELECT e FROM player e WHERE e.title_sigla_player LIKE :titleSiglaPlayer").setParameter("titleSiglaPlayer", "%" + titleSiglaPlayer + "%").getResultList();
    }

    public List<Player> listInOrderTitleSiglaPlayer() {
        return em.createQuery("SELECT e FROM player e ORDER BY e.title_sigla_player").getResultList();
    }

    public List<Player> listByProfilePictureUrl(String profilePictureUrl) {
        return em.createQuery("SELECT e FROM player e WHERE e.profile_picture_url LIKE :profilePictureUrl").setParameter("profilePictureUrl", "%" + profilePictureUrl + "%").getResultList();
    }

    public List<Player> listInOrderProfilePictureUrl() {
        return em.createQuery("SELECT e FROM player e ORDER BY e.profile_picture_url").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Player> lf;
        if (order.equals("idPlayer")) {
            lf = listInOrderIdPlayer();
        } else if (order.equals("namePlayer")) {
            lf = listInOrderNamePlayer();
        } else if (order.equals("pointsPlayer")) {
            lf = listInOrderPointsPlayer();
        } else if (order.equals("birthdayPlayer")) {
            lf = listInOrderBirthdayPlayer();
        } else if (order.equals("federationSiglaPlayer")) {
            lf = listInOrderFederationSiglaPlayer();
        } else if (order.equals("genderSiglaPlayer")) {
            lf = listInOrderGenderSiglaPlayer();
        } else if (order.equals("titleSiglaPlayer")) {
            lf = listInOrderTitleSiglaPlayer();
        } else if (order.equals("profilePictureUrl")) {
            lf = listInOrderProfilePictureUrl();
        } else {
            lf = listInOrderIdPlayer();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdPlayer() + "-" + lf.get(i).getNamePlayer());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOPlayer daoPlayer = new DAOPlayer();
        List<Player> listaPlayer = daoPlayer.list();
        for (Player player : listaPlayer) {
            System.out.println(player);
        }
    }
}
