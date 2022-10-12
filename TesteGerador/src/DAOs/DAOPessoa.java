package DAOs;

import Entidades.Pessoa;
import java.util.ArrayList;
import java.util.List;

public class DAOPessoa extends DAOGenerico<Pessoa> {

    public DAOPessoa() {
        super(Pessoa.class);
    }

    public int autoIdPessoa() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idPessoa) FROM pessoae ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Pessoa> listInOrderIdPessoa() {
        return em.createQuery("SELECT e FROM pessoa e ORDER BY e.id_pessoa").getResultList();
    }

    public List<Pessoa> listByNomePessoa(String nomePessoa) {
        return em.createQuery("SELECT e FROM pessoa e WHERE e.nome_pessoa LIKE :nomePessoa").setParameter("nomePessoa", "%" + nomePessoa + "%").getResultList();
    }

    public List<Pessoa> listInOrderNomePessoa() {
        return em.createQuery("SELECT e FROM pessoa e ORDER BY e.nome_pessoa").getResultList();
    }

    public List<Pessoa> listInOrderDataNascimentoPessoa() {
        return em.createQuery("SELECT e FROM pessoa e ORDER BY e.data_nascimento_pessoa").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Pessoa> lf;
        if (order.equals("idPessoa")) {
            lf = listInOrderIdPessoa();
        } else if (order.equals("nomePessoa")) {
            lf = listInOrderNomePessoa();
        } else if (order.equals("dataNascimentoPessoa")) {
            lf = listInOrderDataNascimentoPessoa();
        } else {
            lf = listInOrderIdPessoa();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdPessoa() + "-" + lf.get(i).getNomePessoa());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOPessoa daoPessoa = new DAOPessoa();
        List<Pessoa> listaPessoa = daoPessoa.list();
        for (Pessoa pessoa : listaPessoa) {
            System.out.println(pessoa);
        }
    }
}
