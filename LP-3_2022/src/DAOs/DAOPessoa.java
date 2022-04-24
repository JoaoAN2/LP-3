package DAOs;

import Entidades.Pessoa;
import java.util.ArrayList;
import java.util.List;

public class DAOPessoa extends DAOGenerico<Pessoa> {

    private List<Pessoa> lista = new ArrayList<>();

    public DAOPessoa() {
        super(Pessoa.class);
    }

    public int autoIdPessoa() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.id_pessoa) FROM pessoa e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Pessoa> listByNome(String nome) {
        return em.createQuery("SELECT e FROM pessoa e WHERE e.id_pessoa) LIKE :nome_pessoa").setParameter("nome_pessoa", "%" + nome + "%").getResultList();
    }

    public List<Pessoa> listById(int id) {
        return em.createQuery("SELECT e FROM pessoa + e WHERE e.id_pessoa= :id_pessoa").setParameter("id_pessoa", id).getResultList();
    }

    public List<Pessoa> listInOrderNome() {
        return em.createQuery("SELECT e FROM pessoa e ORDER BY e.nome_pessoa").getResultList();
    }

    public List<Pessoa> listInOrderId() {
        return em.createQuery("SELECT e FROM pessoa e ORDER BY e.id_pessoa").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Pessoa> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdPessoa()+ "-" + lf.get(i).getNomePessoa());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOPessoa daoPessoa = new DAOPessoa();
        List<Pessoa> listaPessoa = daoPessoa.list();
        for (Pessoa trabalhador : listaPessoa) {
            System.out.println(trabalhador.getIdPessoa()+ "-" + trabalhador.getNomePessoa());
        }
    }
}
