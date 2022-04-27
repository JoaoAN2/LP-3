package DAOs;

import Entidades.Gender;
import java.util.ArrayList;
import java.util.List;

public class DAOGender extends DAOGenerico<Gender> {

    private List<Gender> lista = new ArrayList<>();

    public DAOGender() {
        super(Gender.class);
    }

    public int autoIdGender() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.id_gender) FROM gender e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Gender> listByNome(String nome) {
        return em.createQuery("SELECT e FROM gender e WHERE e.id_gender) LIKE :nome_gender").setParameter("nome_gender", "%" + nome + "%").getResultList();
    }

    public List<Gender> listById(int id) {
        return em.createQuery("SELECT e FROM gender + e WHERE e.id_gender= :id_gender").setParameter("id_gender", id).getResultList();
    }

    public List<Gender> listInOrderNome() {
        return em.createQuery("SELECT e FROM gender e ORDER BY e.nome_gender").getResultList();
    }

    public List<Gender> listInOrderId() {
        return em.createQuery("SELECT e FROM gender e ORDER BY e.id_gender").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Gender> lf;
        if (qualOrdem.equals("sigla")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSiglaGender()+ "-" + lf.get(i).getFullGender());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOGender daoGender = new DAOGender();
        List<Gender> listaGender = daoGender.list();
        for (Gender gender : listaGender) {
            System.out.println(gender.getSiglaGender()+ "-" + gender.getFullGender());
        }
    }
}
