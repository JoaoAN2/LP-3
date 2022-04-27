package DAOs;

import Entidades.Title;
import java.util.ArrayList;
import java.util.List;

public class DAOTitle extends DAOGenerico<Title> {

    private List<Title> lista = new ArrayList<>();

    public DAOTitle() {
        super(Title.class);
    }

    public int autoIdTitle() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.id_title) FROM title e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Title> listByNome(String nome) {
        return em.createQuery("SELECT e FROM title e WHERE e.id_title) LIKE :nome_title").setParameter("nome_title", "%" + nome + "%").getResultList();
    }

    public List<Title> listById(int id) {
        return em.createQuery("SELECT e FROM title + e WHERE e.id_title= :id_title").setParameter("id_title", id).getResultList();
    }

    public List<Title> listInOrderNome() {
        return em.createQuery("SELECT e FROM title e ORDER BY e.nome_title").getResultList();
    }

    public List<Title> listInOrderId() {
        return em.createQuery("SELECT e FROM title e ORDER BY e.id_title").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Title> lf;
        if (qualOrdem.equals("sigla")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getSiglaTitle()+ "-" + lf.get(i).getNameTitle());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOTitle daoTitle = new DAOTitle();
        List<Title> listaTitle = daoTitle.list();
        for (Title title : listaTitle) {
            System.out.println(title.getSiglaTitle()+ "-" + title.getNameTitle());
        }
    }
}
