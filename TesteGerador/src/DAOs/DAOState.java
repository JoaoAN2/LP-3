package DAOs;

import Entidades.State;
import java.util.ArrayList;
import java.util.List;

public class DAOState extends DAOGenerico<State> {

    public DAOState() {
        super(State.class);
    }

    public List<State> listBySiglaState(String siglaState) {
        return em.createQuery("SELECT e FROM State e WHERE e.siglaState LIKE :siglaState").setParameter("siglaState", "%" + siglaState + "%").getResultList();
    }

    public List<State> listInOrderSiglaState() {
        return em.createQuery("SELECT e FROM State e ORDER BY e.siglaState").getResultList();
    }

    public List<State> listByNameState(String nameState) {
        return em.createQuery("SELECT e FROM State e WHERE e.nameState LIKE :nameState").setParameter("nameState", "%" + nameState + "%").getResultList();
    }

    public List<State> listInOrderNameState() {
        return em.createQuery("SELECT e FROM State e ORDER BY e.nameState").getResultList();
    }

    public List<State> listByFederationIdState(String federationIdState) {
        return em.createQuery("SELECT e FROM State e WHERE e.federationIdState LIKE :federationIdState").setParameter("federationIdState", "%" + federationIdState + "%").getResultList();
    }

    public List<State> listInOrderFederationIdState() {
        return em.createQuery("SELECT e FROM State e ORDER BY e.federationIdState").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<State> lf;
        if (order.equals("siglaState")) {
            lf = listInOrderSiglaState();
        } else if (order.equals("nameState")) {
            lf = listInOrderNameState();
        } else if (order.equals("federationIdState")) {
            lf = listInOrderFederationIdState();
        } else {
            lf = listInOrderSiglaState();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSiglaState() + "-" + lf.get(i).getNameState());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOState daoState = new DAOState();
        List<State> listaState = daoState.list();
        for (State state : listaState) {
            System.out.println(state);
        }
    }
}
