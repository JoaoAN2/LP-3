package DAOs;

import Entidades.Funcionario;
import java.util.ArrayList;
import java.util.List;

public class DAOFuncionario extends DAOGenerico<Funcionario> {

    public DAOFuncionario() {
        super(Funcionario.class);
    }

    public int autoIdFuncionario() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.pessoaIdFuncionario) FROM funcionarioe ").getSingleResult();
        if(a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }
    public List<Funcionario> listInOrderPessoaIdFuncionario() {
        return em.createQuery("SELECT e FROM funcionario e ORDER BY e.pessoa_id_funcionario").getResultList();
    }

    public List<Funcionario> listInOrderCargoIdFuncionario() {
        return em.createQuery("SELECT e FROM funcionario e ORDER BY e.cargo_id_funcionario").getResultList();
    }

    public List<String> listInOrderString(String order) {
        List<Funcionario> lf;
        if (order.equals("pessoaIdFuncionario")) {
            lf = listInOrderPessoaIdFuncionario();
        } else if (order.equals("cargoIdFuncionario")) {
            lf = listInOrderCargoIdFuncionario();
        } else {
            lf = listInOrderPessoaIdFuncionario();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getPessoaIdFuncionario() + "-" + lf.get(i).getCargoIdFuncionario());
        }
        return ls;
    }

    public static void main(String[] args) {
        DAOFuncionario daoFuncionario = new DAOFuncionario();
        List<Funcionario> listaFuncionario = daoFuncionario.list();
        for (Funcionario funcionario : listaFuncionario) {
            System.out.println(funcionario);
        }
    }
}
