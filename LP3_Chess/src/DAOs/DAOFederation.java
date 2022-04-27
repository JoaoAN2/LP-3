package DAOs;

import Entidades.Federation;
import java.util.ArrayList;
import java.util.List;

public class DAOFederation extends DAOGenerico<Federation> {

    private List<Federation> lista = new ArrayList<>();

    public DAOFederation() {
        super(Federation.class);
    }

    public int autoIdFederation() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.id_federation) FROM federation e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Federation> listByNome(String nome) {
        return em.createQuery("SELECT e FROM federation e WHERE e.id_federation) LIKE :nome_federation").setParameter("nome_federation", "%" + nome + "%").getResultList();
    }

    public List<Federation> listById(int id) {
        return em.createQuery("SELECT e FROM federation + e WHERE e.id_federation= :id_federation").setParameter("id_federation", id).getResultList();
    }

    public List<Federation> listInOrderNome() {
        return em.createQuery("SELECT e FROM federation e ORDER BY e.nome_federation").getResultList();
    }

    public List<Federation> listInOrderId() {
        return em.createQuery("SELECT e FROM federation e ORDER BY e.id_federation").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Federation> lf;
        if (qualOrdem.equals("sigla")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSiglaFederation()+ "-" + lf.get(i).getNameFederation());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOFederation daoFederation = new DAOFederation();
        List<Federation> listaFederation = daoFederation.list();
        for (Federation trabalhador : listaFederation) {
            System.out.println(trabalhador.getSiglaFederation()+ "-" + trabalhador.getNameFederation());
        }
    }
}
