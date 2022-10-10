package DAOs;

import Entidades.Login;
import java.util.ArrayList;
import java.util.List;

public class DAOLogin extends DAOGenerico<Login> {

    public DAOLogin() {
        super(Login.class);
    }

    public List<Login> listByEmail(String email) {
        return em.createQuery("SELECT e FROM login e WHERE e.email LIKE :email").setParameter("email", "%" + email + "%").getResultList();
    }

    public List<Login> listInOrderEmail() {
        return em.createQuery("SELECT e FROM login e ORDER BY e.email").getResultList();
    }

    public List<Login> listByPassword(String password) {
        return em.createQuery("SELECT e FROM login e WHERE e.password LIKE :password").setParameter("password", "%" + password + "%").getResultList();
    }

    public List<Login> listInOrderPassword() {
        return em.createQuery("SELECT e FROM login e ORDER BY e.password").getResultList();
    }

    public List<Login> listInOrderAutority() {
        return em.createQuery("SELECT e FROM login e ORDER BY e.autority").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Login> lf;
        if (order.equals("email")) {
            lf = listInOrderEmail();
        } else if (order.equals("password")) {
            lf = listInOrderPassword();
        } else if (order.equals("autority")) {
            lf = listInOrderAutority();
        } else {
            lf = listInOrderEmail();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getEmail() + "-" + lf.get(i).getPassword());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOLogin daoLogin = new DAOLogin();
        List<Login> listaLogin = daoLogin.list();
        for (Login login : listaLogin) {
            System.out.println(login);
        }
    }
}
